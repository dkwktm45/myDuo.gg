package com.project.MyDuo.dto.LoL.MatchData;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "info"
})

@Getter @Setter
@ToString
public class MatchDataDto {
    @JsonProperty("info")
    private InfoDto infodto;

    public MatchDataDto() {}

    public MatchDataDto(InfoDto infodto) {
        this.infodto = infodto;
    }
}
