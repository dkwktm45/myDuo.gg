package com.project.MyDuo.dto;

import com.project.MyDuo.dto.Board.BoardCreationDto;
import com.project.MyDuo.dto.LoL.Info.*;
import com.project.MyDuo.dto.LoL.LoLAccountDto;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.Board;
import com.project.MyDuo.entity.LoLAccount.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component @RequiredArgsConstructor
public class Mapper {
    public LoLAccountDto toLoLAccountDto(LoLBasicInfoDto basic, LoLDetailInfoDto detail) {
        return new LoLAccountDto(basic.getId(),
                basic.getPuuid(),
                basic.getName(),
                detail.getTier(),
                detail.getTier().compareTo("UNRANKED") == 0 ? "" : detail.getRank(),
                detail.getWins(),
                detail.getLosses(),
                basic.getSummonerLevel(),
                detail.getLeaguePoints());
    }

    public LoLAccount toLoLAccount(LoLAccountDto dto) {
        LoLAccount account = LoLAccount.builder()
                .summonerId(dto.getId())
                .puuid(dto.getPuuid())
                .name(dto.getName())
                .rank(dto.getRank())
                .tier(dto.getTier())
                .wins(dto.getWins())
                .losses(dto.getLosses())
                .level(dto.getSummonerLevel())
                .leaguePoints(dto.getLeaguePoints())
                .build();
        account.setScore(dto.getScoreResult());
        account.setLanes();
        account.setChampionMap(dto.getChampionDtoMap());

        return  account;
    }

    public LoLAccountInfoDto toLoLAccountInfoDto(LoLAccount account) {
        LoLAccountInfoDto accountInfoDto = new LoLAccountInfoDto(
                account.getName(),
                account.getTier(),
                account.getRank(),
                account.getWins(),
                account.getLosses(),
                account.getLeaguePoints()
        );
        // laneInfo 설정.
        List<Lane> lanes = account.getLanes()
                .stream()
                .sorted(Comparator.comparing(Lane::getCount).reversed())
                .collect(Collectors.toList());

        accountInfoDto.getLaneInfo().add(lanes.get(0).getLane().ordinal());
        accountInfoDto.getLaneInfo().add(lanes.get(1).getLane().ordinal());

        // championInfo 설정.
        List<ChampionInfoDto> championInfo = accountInfoDto.getChampionInfo();
        for (Champion champion : account.getChampions())
            championInfo.add(toChampionInfoDto(champion));

        championInfo.sort(Comparator.comparing(ChampionInfoDto::getTotalPlayedCount).reversed());
        // resultRecentGameDto 설정.
        accountInfoDto.setResultRecentGameDto(toResultRecentGameInfoDto(account.getScore()));

        return accountInfoDto;
    }

    public ChampionInfoDto toChampionInfoDto(Champion champion) {
        return new ChampionInfoDto(champion.getName()
        , champion.getWins() + champion.getLosses()
                , champion.getMinionKilled()
                , champion.getKills()
        , champion.getDeaths()
        , champion.getAssists()
        , champion.getWins()
        );
    }

    public ResultRecentGameInfoDto toResultRecentGameInfoDto (Score score) {
        String result = score.getResult();
        int wins = 0, losses = 0;

        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == 'W') wins++;
            else losses++;
        }

        return new ResultRecentGameInfoDto(result
        , wins, losses
        , wins == 0 ? 0d : (losses == 0 ? 100.0 : (wins / (double)(wins + losses)) * 100));
    }

    public LaneType toLaneType(int value) {
        if (value == 0)
            return LaneType.TOP;
        else if (value == 1)
            return LaneType.JUNGLE;
        else if (value == 2)
            return LaneType.MID;
        else if (value == 3)
            return LaneType.BOT;
        else
            return LaneType.SUP;
    }


    public Board toBoard(Member user, BoardCreationDto creationDto) {
        Board board = Board.builder()
                .uuid(UUID.randomUUID().toString())
                .lolPuuid(creationDto.getLolPuuid())
                .name(user.getName())
                .content(creationDto.getContent())
                .closingStatus(false)
                .micEnabled(creationDto.isMicEnabled())
                .registrationTime(new Timestamp(System.currentTimeMillis()))
                .myPositions(creationDto.getMyPositions())
                .otherPositions(creationDto.getOtherPositions())
                .member(user)
                .build();
        return board;
    }

    /*public BoardBasicDto toBoardBasicDto(Board entity, String summonerName, String summonerRank) {
        return new BoardBasicDto(
                entity.getUuid(),
                entity.getContent(),
                new CopyOnWriteArrayList<>() {{
                    add(entity.getMyPosition1().ordinal());
                    add(entity.getMyPosition2().ordinal());
                }},
                new CopyOnWriteArrayList<>() {{
                    add(entity.getOpponentPosition1().ordinal());
                    add(entity.getOpponentPosition2().ordinal());
                }},
                summonerName,
                summonerRank,
                entity.isClosingStatus()
        );
    }*/
}
