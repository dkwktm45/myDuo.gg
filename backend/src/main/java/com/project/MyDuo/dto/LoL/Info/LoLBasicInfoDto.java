package com.project.MyDuo.dto.LoL.Info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.ToString;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "puuid",
        "name",
        "summonerLevel"
})

@ToString
@Generated("jsonschema2pojo")
public class LoLBasicInfoDto {

    @JsonProperty("id")
    private String id;
    @JsonProperty("puuid")
    private String puuid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("summonerLevel")
    private Integer summonerLevel;

    public LoLBasicInfoDto() {
    }

    public LoLBasicInfoDto(String id, String puuid, String name, Integer summonerLevel) {
        this.id = id;
        this.puuid = puuid;
        this.name = name;
        this.summonerLevel = summonerLevel;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("puuid")
    public String getPuuid() {
        return puuid;
    }

    @JsonProperty("puuid")
    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("summonerLevel")
    public Integer getSummonerLevel() {
        return summonerLevel;
    }

    @JsonProperty("summonerLevel")
    public void setSummonerLevel(Integer summonerLevel) {
        this.summonerLevel = summonerLevel;
    }
}