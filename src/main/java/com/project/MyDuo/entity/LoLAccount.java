package com.project.MyDuo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity @Getter @Setter
public class LoLAccount {
    @Id @GeneratedValue
    @Column(name = "LOL_ACCOUNT_ID")
    private Long id;

    @Column(name = "SUMMONER_ID", unique = true)
    private String summonerId;
    @Column(name = "PUUID", unique = true)
    private String puuid;
    @Column(name = "SUMMONER_NAME")
    private String summonerName;

    private String tier, rank;

    @Column(name = "WIN_COUNT")
    private Integer wins;
    @Column(name = "LOSE_COUNT")
    private Integer losses;

    @Column(name = "SUMMONER_LEVEL")
    private Integer summonerLevel;
    @Column(name = "LEAGUE_POINTS")
    private Integer leaguePoints;

    @OneToMany(mappedBy = "lane")
    private List<Lane> lanes = new CopyOnWriteArrayList<>();
    @OneToMany(mappedBy = "champion")
    private Map<String, Champion> championMap = new ConcurrentHashMap<>();

    public LoLAccount() {
    }
}
