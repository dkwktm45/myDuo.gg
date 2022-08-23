package com.project.MyDuo.dto.LoL.Info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.ToString;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "queueType",
        "tier",
        "rank",
        "leaguePoints",
        "wins",
        "losses"
})

@ToString
@Generated("jsonschema2pojo")
public class LoLDetailInfoDto {
    @JsonProperty("queueType")
    private String queueType;
    @JsonProperty("tier")
    private String tier;
    @JsonProperty("rank")
    private String rank;
    @JsonProperty("leaguePoints")
    private Integer leaguePoints;
    @JsonProperty("wins")
    private Integer wins;
    @JsonProperty("losses")
    private Integer losses;

    public LoLDetailInfoDto() {}

    public LoLDetailInfoDto(String queueType, String tier, String rank, Integer leaguePoints, Integer wins, Integer losses) {
        this.queueType = queueType;
        this.tier = tier;
        this.rank = rank;
        this.leaguePoints = leaguePoints;
        this.wins = wins;
        this.losses = losses;
    }

    @JsonProperty("queueType")
    public String getQueueType() {
        return queueType;
    }

    @JsonProperty("queueType")
    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    @JsonProperty("tier")
    public String getTier() {
        return tier;
    }

    @JsonProperty("tier")
    public void setTier(String tier) {
        this.tier = tier;
    }

    @JsonProperty("rank")
    public String getRank() {
        return rank;
    }

    @JsonProperty("rank")
    public void setRank(String rank) {
        this.rank = rank;
    }

    @JsonProperty("leaguePoints")
    public Integer getLeaguePoints() {
        return leaguePoints;
    }

    @JsonProperty("leaguePoints")
    public void setLeaguePoints(Integer leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    @JsonProperty("wins")
    public Integer getWins() {
        return wins;
    }

    @JsonProperty("wins")
    public void setWins(Integer wins) {
        this.wins = wins;
    }

    @JsonProperty("losses")
    public Integer getLosses() {
        return losses;
    }

    @JsonProperty("losses")
    public void setLosses(Integer losses) {
        this.losses = losses;
    }
}