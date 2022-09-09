package com.project.MyDuo.dto.LoL.Info;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Setter @Getter
public class LoLAccountInfoDto {
    private String name, tier, rank;
    private Integer wins, losses, leaguePoints;
    private Double winningRate;

    private List<Integer> laneInfo = new CopyOnWriteArrayList<>();
    private List<ChampionInfoDto> championInfo = new CopyOnWriteArrayList<>();
    private ResultRecentGameInfoDto resultRecentGameDto;

    public LoLAccountInfoDto() {}

    public LoLAccountInfoDto(String name, String tier, String rank, Integer wins, Integer losses, Integer leaguePoints) {
        this.name = name;
        this.tier = tier;
        this.rank = rank;
        this.wins = wins;
        this.losses = losses;
        this.leaguePoints = leaguePoints;

        winningRate = wins == 0 ? 0d : (losses == 0 ? 100.0 : (wins / (double)(losses + wins)) * 100);
    }
}
