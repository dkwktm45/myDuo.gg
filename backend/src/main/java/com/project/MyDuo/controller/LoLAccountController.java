package com.project.MyDuo.controller;

import com.project.MyDuo.Const.LoLConst;
import com.project.MyDuo.customException.noMoreLoLAccountException;
import com.project.MyDuo.customException.summonerAlreadyRegisteredException;
import com.project.MyDuo.dto.LoL.ChampionDto;
import com.project.MyDuo.dto.LoL.Info.LoLBasicInfoDto;
import com.project.MyDuo.dto.LoL.Info.LoLDetailInfoDto;
import com.project.MyDuo.dto.LoL.LoLAccountDto;
import com.project.MyDuo.dto.LoL.MatchData.MatchDataDto;
import com.project.MyDuo.dto.LoL.MatchData.ParticipantDto;
import com.project.MyDuo.dto.Mapper;
import com.project.MyDuo.entity.LoLAccount.LoLAccount;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.jwt.JwtTokenUtil;
import com.project.MyDuo.security.AuthUser;
import com.project.MyDuo.service.LoLAccountService;
import com.project.MyDuo.service.MemberAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j @Controller
@RequiredArgsConstructor
@RequestMapping("/lol")
public class LoLAccountController {
    private final WebClient basicInfoWebClient;
    private final WebClient detailInfoWebClient;
    private final WebClient matchIdListCWebClient;
    private final WebClient matchDataWebClient;

    private final LoLAccountService loLAccountService;
    private final MemberAccountService memberAccountService;
    private final JwtTokenUtil jwtTokenUtil;

    private final Mapper mapper;

    @PostMapping("/add") @ResponseBody @Transactional
    public String addSummoner(@AuthUser Member member, String summonerName) {// 소환사 등록 메서드.
        //user에 대한 검증.
        if (loLAccountService.countValidLoLAccount(member.getEmail()) == 5)
            throw new noMoreLoLAccountException("한 회원당 최대 5개의 LoL계정을 등록할 수 있습니다.");

        //summonerName에 대한 검증.
        LoLBasicInfoDto basicInfoDto = getLoLBasicInfoDtoWithName(summonerName);
        LoLAccount loLAccount = loLAccountService.findByPuuid(basicInfoDto.getPuuid());
        if(loLAccount != null) {
            //loLAccount.getUser().removeLoLAccount(loLAccount.getPuuid());
            if (loLAccount.isValid())
                throw new summonerAlreadyRegisteredException("해당 소환사 계정이 이미 등록되어 있습니다.");
            else {
                loLAccount.activate();
                updateSummoner(loLAccount.getPuuid());
            }
        }else {
            // LoL 계정 entity 생성.
            LoLDetailInfoDto detailInfoDto = getLoLDetailInfoDto(basicInfoDto.getId());
            LoLAccountDto accountDto = mapper.toLoLAccountDto(basicInfoDto, detailInfoDto);

            if (accountDto.getWins() + accountDto.getLosses() != 0) {
                reflectMatchResults(accountDto, accountDto.getWins() + accountDto.getLosses());
            }
            loLAccount = mapper.toLoLAccount(accountDto);
        }

        //User에 account등록.
        memberAccountService.addLoLAccount(member.getEmail(), loLAccount);
        loLAccountService.save(loLAccount);

        return "소환사 등록이 완료되었습니다.";
    }

    @PutMapping("/update") @ResponseBody
    public String updateSummoner(String encryptedPUUID) {// 소환사 정보 갱신 메서드.
        LoLBasicInfoDto basicInfoDto = getLoLBasicInfoDtoWithPuuid(encryptedPUUID);
        LoLDetailInfoDto detailInfoDto =  getLoLDetailInfoDto(basicInfoDto.getId());
        LoLAccountDto accountDto = mapper.toLoLAccountDto(basicInfoDto, detailInfoDto);
        LoLAccount loLAccount = loLAccountService.findByPuuid(accountDto.getPuuid());

        int dtoCount = accountDto.getWins() + accountDto.getLosses();
        int entityCount = loLAccount.getWins() + loLAccount.getLosses();

        if (entityCount != dtoCount) {
            log.info("총 {}건의 data update 예정.", dtoCount - entityCount);
            reflectMatchResults(accountDto, dtoCount - entityCount);
        }

        // 새로운 결과 반영.
        loLAccount.update(accountDto);
        loLAccountService.save(loLAccount);

        return "소환사 정보가 업데이트되었습니다.";
    }

    @PostMapping("/remove") @ResponseBody
    public String deleteSummoner(@RequestHeader String authorization, String encryptedPUUID) {
        String email = jwtTokenUtil.getEmail(authorization.split(" ")[1]);
        log.info("삭제 전 계정 수={}", loLAccountService.countValidLoLAccount(email));
        loLAccountService.deleteByPuuid(encryptedPUUID);

        log.info("삭제 후 계정 수={}", loLAccountService.countValidLoLAccount(email));
        return "소환사 계정을 삭제했습니다.";
    }

    private void reflectMatchResults(LoLAccountDto accountDto, int count) {
        int idx = 0, cnt = 0;
        StringBuilder sb = new StringBuilder();

        while (true) {
            List<String> matchIdList = getLoLMatchIdList(accountDto.getPuuid(), idx, 100);

            if (matchIdList.isEmpty()) return;

            for (String matchId : matchIdList) {
                MatchDataDto matchDataDto = getMatchDataDto(matchId);

                ParticipantDto summonerData = matchDataDto.getInfodto().getParticipantDtoList()
                        .stream().filter(summoner -> summoner.getPuuid().equals(accountDto.getPuuid()))
                        .findAny().orElseThrow();

                if (cnt == count) {
                    accountDto.setScoreResult(sb.length() < 10 ? sb.toString() : sb.substring(0, 10));
                    return;
                }

                if (!summonerData.getGameEndedInEarlySurrender()) {
                    log.info("{} {}",accountDto.getName(), summonerData.getChampionName());
                    sb.append(summonerData.getWin() ? "W" : "L");
                    reflectChampionResult(accountDto.getChampionDtoMap(), summonerData);
                    cnt++;
                }
            }
            idx += matchIdList.size();
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
    }

    private LoLBasicInfoDto getLoLBasicInfoDtoWithName(String summonerName) throws WebClientResponseException {
        Mono<LoLBasicInfoDto> basicResult = basicInfoWebClient.get()
                .uri(LoLConst.SUMMONER_BASIC_BY_NAME_URI + "/{summonerName}", summonerName)
                .retrieve()
                .bodyToMono(LoLBasicInfoDto.class);

        return basicResult.block();
    }

    private LoLBasicInfoDto getLoLBasicInfoDtoWithPuuid(String encryptedPUUID) {
        Mono<LoLBasicInfoDto> basicResult = basicInfoWebClient.get()
                .uri(LoLConst.SUMMONER_BASIC_BY_PUUID_URI + "/{encryptedPUUID}", encryptedPUUID)
                .retrieve()
                .bodyToMono(LoLBasicInfoDto.class);

        return basicResult.block();
    }

    private LoLDetailInfoDto getLoLDetailInfoDto(String encryptedSummonerId) throws NullPointerException{
        LoLDetailInfoDto detailInfoDTO;

        List<LoLDetailInfoDto> detailDtoList = detailInfoWebClient.get()
                .uri(LoLConst.SUMMONER_DETAIL_URI + "/{encryptedSummonerId}", encryptedSummonerId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LoLDetailInfoDto>>() {}).block();

        detailInfoDTO = detailDtoList.stream()
                .filter(dto -> dto.getQueueType().equals("RANKED_SOLO_5x5"))
                .findAny().orElse(new LoLDetailInfoDto("RANKED_SOLO_5x5", "UNRANKED", "I", 0, 0, 0));
        return detailInfoDTO;
    }

    private List<String> getLoLMatchIdList(String puuid, int start, int count) throws WebClientResponseException {
        return matchIdListCWebClient.get().
                uri(LoLConst.SUMMONER_MATCH_LIST_URI + "/{puuid}/ids" + "?queue={queue}&start={start}&count={count}", puuid
                        , LoLConst.SOLO_RANK_QUEUE_ID, start, count)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                .retryWhen(
                        Retry.backoff(100, Duration.ofSeconds(7))
                                .filter(throwable -> throwable instanceof  RuntimeException)
                ).block();
    }

    private MatchDataDto getMatchDataDto(String matchId) {
        return matchDataWebClient.get()
                .uri(LoLConst.SUMMONER_MATCH_DATA_URI + "/{matchId}", matchId).retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        response -> Mono.error(new RuntimeException()))
                .bodyToFlux(MatchDataDto.class)
                .retryWhen(
                        Retry.backoff(100, Duration.ofMillis(100)).jitter(0.7)
                                .filter(throwable -> throwable instanceof  RuntimeException)
                ).blockFirst();
    }
}
