package com.project.MyDuo.dto.LoL.Info;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResultRecentGameInfoDto {
    private String result;
    private Integer wins, losses;
    private Double winningRate;

    public ResultRecentGameInfoDto() {}

    public ResultRecentGameInfoDto(String result, Integer wins, Integer losses, Double winningRate) {
        this.result = result;
        this.wins = wins;
        this.losses = losses;
        this.winningRate = winningRate;
    }
}
