package com.project.MyDuo.dto.LoL;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class ChampionDto {
    private String championName;
    private Integer kills, deaths, assists;
    private Integer wins, losses;
    private Integer totalTimePlayed, totalMinionsKilled, neutralMinionsKilled;

    public ChampionDto() {}

    public ChampionDto(String championName) {
        this.championName = championName;
        kills = deaths = assists = 0;
        wins = losses = 0;
        totalTimePlayed = totalMinionsKilled = neutralMinionsKilled = 0;
    }

}
