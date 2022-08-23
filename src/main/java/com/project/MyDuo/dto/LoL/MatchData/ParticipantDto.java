package com.project.MyDuo.dto.LoL.MatchData;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "assists",
        "championName",
        "deaths",
        "gameEndedInEarlySurrender",
        "kills",
        "lane",
        "neutralMinionsKilled",
        "puuid",
        "role",
        "timePlayed",
        "totalMinionsKilled",
        "win"
})

@Getter @Setter @ToString
public class ParticipantDto {
    @JsonProperty("assists")
    private Integer assists;
    @JsonProperty("championName")
    private String championName;
    @JsonProperty("deaths")
    private Integer deaths;
    @JsonProperty("gameEndedInEarlySurrender")
    private Boolean gameEndedInEarlySurrender;
    @JsonProperty("kills")
    private Integer kills;
    @JsonProperty("lane")
    private String lane;
    @JsonProperty("neutralMinionsKilled")
    private Integer neutralMinionsKilled;
    @JsonProperty("puuid")
    private String puuid;
    @JsonProperty("role")
    private String role;
    @JsonProperty("timePlayed")
    private Integer timePlayed;
    @JsonProperty("totalMinionsKilled")
    private Integer totalMinionsKilled;
    @JsonProperty("win")
    private Boolean win;

    public ParticipantDto() {}

}
