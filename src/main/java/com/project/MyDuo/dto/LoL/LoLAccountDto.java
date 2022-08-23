package com.project.MyDuo.dto.LoL;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter @Setter @ToString
public class LoLAccountDto {
    private String id, puuid, name;
    private String tier, rank;
    private Integer wins, losses;
    private Integer summonerLevel;
    private Integer leaguePoints;

    private Map<String, ChampionDto> championDtoMap;

    public LoLAccountDto() {}

    public LoLAccountDto(String id, String puuid, String name, String tier, String rank, Integer wins, Integer losses, Integer summonerLevel, Integer leaguePoints) {
        this.id = id;
        this.puuid = puuid;
        this.name = name;
        this.tier = tier;
        this.rank = rank;
        this.wins = wins;
        this.losses = losses;
        this.summonerLevel = summonerLevel;
        this.leaguePoints = leaguePoints;

        championDtoMap = new ConcurrentHashMap<>();
    }
}
