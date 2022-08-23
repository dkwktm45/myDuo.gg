package com.project.MyDuo.controller;


import com.project.MyDuo.Const.LoLConst;
import com.project.MyDuo.dto.LoL.ChampionDto;
import com.project.MyDuo.dto.LoL.Info.ChampionInfoDto;
import com.project.MyDuo.dto.LoL.Info.LoLBasicInfoDto;
import com.project.MyDuo.dto.LoL.Info.LoLDetailInfoDto;
import com.project.MyDuo.dto.LoL.LoLAccountDto;
import com.project.MyDuo.dto.LoL.MatchData.MatchDataDto;
import com.project.MyDuo.dto.LoL.MatchData.ParticipantDto;
import com.project.MyDuo.factory.WebClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller @Slf4j
@RequestMapping("/lol")
public class LoLAccountController {
    @Autowired
    private WebClientFactory webClientFactory;
    private static WebClient detailWebClient;
    private static WebClient basicWebClient;
    private static WebClient matchDataWebClient;
    private static WebClient matchIdWebClient;

    static int count = 0;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @GetMapping("/add")
    public String addForm(@ModelAttribute("accountForm") LoLBasicInfoDto accountDTO) {return "lol/addForm";}

    @PostMapping("/add") @ResponseBody
    public Map<String, ChampionInfoDto> addSummoner(String summonerName) {
        basicWebClient = webClientFactory.getLoLBasicAccountRequestClient();
        detailWebClient = webClientFactory.getLoLDetailAccountRequestClient();
        matchIdWebClient = webClientFactory.getLoLMatchIdListRequestClient();
        matchDataWebClient = webClientFactory.getMatchDataRequestClient();

        LoLBasicInfoDto basicInfoDTO = null;
        LoLDetailInfoDto detailInfoDTO = null;
        LoLAccountDto accountDto = null;

        long startTime = System.currentTimeMillis();
        try {
            basicInfoDTO = getLoLBasicInfoDTO(summonerName);
            detailInfoDTO = getLoLDetailInfoDTO(basicInfoDTO.getId());

            accountDto = getLoLAccountDto(basicInfoDTO, detailInfoDTO);

            if (accountDto.getWins() + accountDto.getLosses() != 0)
                reflectMatchResults(accountDto);

        } catch (Exception e) {
            log.info("여기서 예외 잡았음");
            e.printStackTrace();
        }

        Map<String, ChampionInfoDto> championInfoDtoMap = new ConcurrentHashMap<>();
        for (ChampionDto dto : accountDto.getChampionDtoMap().values()) {
            championInfoDtoMap.put(dto.getChampionName(), new ChampionInfoDto(
                    dto.getChampionName(), dto.getWins() + dto.getLosses()
                    , dto.getTotalMinionsKilled() + dto.getNeutralMinionsKilled()
                    , dto.getKills(), dto.getDeaths(), dto.getAssists(), dto.getWins()
            ));
        }
        long endTime = System.currentTimeMillis();
        log.info("finish {}", (endTime - startTime) / 60000);

        return championInfoDtoMap;
    }

    private void reflectMatchResults(LoLAccountDto accountDto) {
        int cnt = 0, max = accountDto.getWins() + accountDto.getLosses(), idx = 0;

        while (true) {
            List<String> matchIdList = getLoLMatchIdList(accountDto.getPuuid(), idx, 100);
            log.info("size={}", matchIdList.size());

            if (matchIdList.isEmpty()) break;

            for (String matchId : matchIdList) {
                MatchDataDto matchDataDto = getMatchDataDto(matchId);
                Date date = new Date(matchDataDto.getInfodto().getGameCreation());

                ParticipantDto summonerData = matchDataDto.getInfodto().getParticipantDtoList()
                        .stream().filter(summoner -> summoner.getPuuid().equals(accountDto.getPuuid()))
                        .findAny().orElseThrow();

                if (cnt == max) return;

                //log.info("{}", sdf.format(date));
                if (!summonerData.getGameEndedInEarlySurrender()) {
                    // Chapmpion별 승률 기록.
                    log.info("{} {}",accountDto.getName(), summonerData.getChampionName());
                    reflectChampionResult(accountDto.getChampionDtoMap(), summonerData);
                    cnt++;
                }
            }

            idx += 20;
        }
    }

    private void reflectChampionResult(Map<String, ChampionDto> map, ParticipantDto dto) {
        boolean result = dto.getWin();

        ChampionDto championDto = map.getOrDefault(dto.getChampionName(), new ChampionDto(dto.getChampionName()));

        if (result)
            championDto.setWins(championDto.getWins() + 1);
        else
            championDto.setLosses(championDto.getLosses() + 1);

        championDto.setKills(championDto.getKills() + dto.getKills());
        championDto.setDeaths(championDto.getDeaths() + dto.getDeaths());
        championDto.setAssists(championDto.getAssists() + dto.getAssists());

        championDto.setNeutralMinionsKilled(championDto.getNeutralMinionsKilled() + dto.getNeutralMinionsKilled());
        championDto.setTotalMinionsKilled(championDto.getTotalMinionsKilled() + dto.getTotalMinionsKilled());
        championDto.setTotalTimePlayed(championDto.getTotalTimePlayed() + dto.getTimePlayed());

        map.put(dto.getChampionName(), championDto);

        count++;
    }

    private LoLAccountDto getLoLAccountDto(LoLBasicInfoDto basicInfoDTO, LoLDetailInfoDto detailInfoDTO) {
        return new LoLAccountDto(basicInfoDTO.getId(), basicInfoDTO.getPuuid(), basicInfoDTO.getName()
                , detailInfoDTO.getTier(), detailInfoDTO.getRank(), detailInfoDTO.getWins(), detailInfoDTO.getLosses()
                , basicInfoDTO.getSummonerLevel(), detailInfoDTO.getLeaguePoints());
    }

    private MatchDataDto getMatchDataDto(String matchId) {
        return matchDataWebClient.get()
                .uri(LoLConst.SUMMONER_MATCH_DATA_URI + "/{matchId}", matchId).retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        response -> Mono.error(new RuntimeException()))
                .bodyToFlux(MatchDataDto.class)
                .retryWhen(
                        /*Retry.fixedDelay(100, Duration.ofMinutes(1))
                                .filter(throwable -> throwable instanceof  RuntimeException)*/
                        Retry.backoff(100, Duration.ofMillis(100)).jitter(0.7)
                                .filter(throwable -> throwable instanceof  RuntimeException)
                ).blockFirst();
    }

    private LoLDetailInfoDto getLoLDetailInfoDTO(String encryptedSummonerId) throws NullPointerException{
        LoLDetailInfoDto detailInfoDTO;

        List<LoLDetailInfoDto> detailDtoList = detailWebClient.get()
                .uri(LoLConst.SUMMONER_DETAIL_URI + "/{encryptedSummonerId}", encryptedSummonerId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LoLDetailInfoDto>>() {}).block();

        detailInfoDTO = detailDtoList.stream()
                .filter(dto -> dto.getQueueType().equals("RANKED_SOLO_5x5"))
                .findAny().orElse(new LoLDetailInfoDto("RANKED_SOLO_5x5", "UNRANKED", "I", 0, 0, 0));
        return detailInfoDTO;
    }

    private LoLBasicInfoDto getLoLBasicInfoDTO(String summonerName) throws WebClientResponseException {
        Mono<LoLBasicInfoDto> basicResult = basicWebClient.get()
                .uri(LoLConst.SUMMONER_BASIC_URI + "/{summonerName}", summonerName)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK))
                        return response.bodyToMono(LoLBasicInfoDto.class);
                    else
                        return response.createException().flatMap(Mono::error);
                });

        return basicResult.block();
    }

    private List<String> getLoLMatchIdList(String puuid, int start, int count) throws WebClientResponseException {
        return matchIdWebClient.get().
                uri(LoLConst.SUMMONER_MATCH_LIST_URI + "/{puuid}/ids" + "?queue={queue}&start={start}&count={count}", puuid
                        , LoLConst.SOLO_RANK_QUEUE_ID, start, count)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                .retryWhen(
                        Retry.fixedDelay(100, Duration.ofMinutes(1))
                                .filter(throwable -> throwable instanceof  RuntimeException)
                        /*Retry.backoff(100, Duration.ofSeconds(7))
                                .filter(throwable -> throwable instanceof  RuntimeException)*/
                ).block();


    }

}

