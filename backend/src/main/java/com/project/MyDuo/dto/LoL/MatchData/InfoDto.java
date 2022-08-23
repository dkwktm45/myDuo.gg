package com.project.MyDuo.dto.LoL.MatchData;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "gameCreation",
        "gameDuration",
        "participants",
})

@Getter @Setter
@ToString
public class InfoDto {
    @JsonProperty("gameCreation")
    private Long gameCreation;

    @JsonProperty("gameDuration")
    private Long gameDuration;

    @JsonProperty("participants")
    private List<ParticipantDto> participantDtoList;

    public InfoDto() {}
}
