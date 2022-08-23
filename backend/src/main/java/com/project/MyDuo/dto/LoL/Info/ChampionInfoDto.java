package com.project.MyDuo.dto.LoL.Info;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChampionInfoDto {
    String championName;
    Integer totalPlayedCount;
    Double kills, deaths, assists;
    Double csPerGame, grade, winRate;


    public ChampionInfoDto() {}

    public ChampionInfoDto(String championName, Integer totalPlayedCount, Integer cs, Integer kills, Integer deaths, Integer assists, Integer wins) {
        this.championName = championName;
        this.totalPlayedCount = totalPlayedCount;
        this.csPerGame = cs / totalPlayedCount.doubleValue();
        this.kills = kills / totalPlayedCount.doubleValue();
        this.deaths = deaths / totalPlayedCount.doubleValue();
        this.assists = assists / totalPlayedCount.doubleValue();

        this.grade = this.deaths == 0d ? (this.kills + this.assists) * 1.20
                : (this.kills + this.assists) / this.deaths;
        this.winRate = wins / totalPlayedCount.doubleValue();
    }
}
