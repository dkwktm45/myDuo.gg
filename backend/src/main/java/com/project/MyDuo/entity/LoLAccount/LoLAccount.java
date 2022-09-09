package com.project.MyDuo.entity.LoLAccount;

import com.project.MyDuo.dto.LoL.ChampionDto;
import com.project.MyDuo.dto.LoL.LoLAccountDto;
import com.project.MyDuo.entity.Member;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity @Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoLAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lol_account_id")
    private Long id;

    @Column(name = "summoner_id", unique = true)
    private String summonerId;
    @Column(name = "puuid", unique = true)
    private String puuid;
    @Column(name = "summoner_name")
    private String name;

    //Rank게임을 하지 않은 사용자들을 위한 기본 값 설정.
    @Builder.Default private String rank = "UNRANKED";
    @Builder.Default private String tier = "";

    @Column(name = "win_count")
    @Builder.Default private Integer wins = 0;
    @Column(name = "lose_count")
    @Builder.Default private Integer losses = 0;

    @Column(name = "summoner_level")
    @Builder.Default private Integer level = 0;
    @Column(name = "league_points")
    @Builder.Default private Integer leaguePoints = 0;
    @Builder.Default private boolean valid = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member user;

    @OneToOne(mappedBy = "loLAccount", cascade = CascadeType.ALL)
    private Score score;

    @OneToMany(mappedBy = "loLAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default private List<Lane> lanes = new CopyOnWriteArrayList<>();

    @OneToMany(mappedBy = "loLAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default private List<Champion> champions = new CopyOnWriteArrayList<>();

    public void changeUser(Member user) {
        this.user = user;

        if (!user.getLolAccounts().contains(this))
            user.getLolAccounts().add(this);
    }

    public void setScore(String result) {
        this.score = new Score(result, this);
    }

    public void setLanes() {
       lanes.add(new Lane(LaneType.TOP,this));
       lanes.add(new Lane(LaneType.JUNGLE,this));
       lanes.add(new Lane(LaneType.MID, this));
       lanes.add(new Lane(LaneType.BOT, this));
       lanes.add(new Lane(LaneType.SUP, this));
    }

    public void activate() { this.valid = true; }

    public void setChampionMap(Map<String, ChampionDto> championDtoMap) {
        for (ChampionDto dto : championDtoMap.values()) {
            champions.add(
                    Champion.builder()
                            .name(dto.getChampionName())
                            .kills(dto.getKills())
                            .deaths(dto.getDeaths())
                            .assists(dto.getAssists())
                            .wins(dto.getWins())
                            .losses(dto.getLosses())
                            .minTime(dto.getTotalTimePlayed())
                            .minionKilled(dto.getNeutralMinionsKilled() + dto.getTotalMinionsKilled())
                            .loLAccount(this)
                            .build()
            );
        }
    }

    @Transactional
    public void update(LoLAccountDto accountDto) {
        if (!this.name.equals(accountDto.getName()))
            this.name = accountDto.getName();
        if (!this.tier.equals(accountDto.getTier()))
            this.tier = accountDto.getTier();
        if (!this.rank.equals(accountDto.getRank()))
            this.rank = accountDto.getRank();
        if (this.wins.compareTo(accountDto.getWins()) != 0)
            this.wins = accountDto.getWins();
        if (this.losses.compareTo(accountDto.getLosses()) != 0)
            this.losses = accountDto.getLosses();
        if (this.level.compareTo(accountDto.getSummonerLevel()) != 0)
            this.level = accountDto.getSummonerLevel();
        if (this.leaguePoints.compareTo(accountDto.getLeaguePoints()) != 0)
            this.leaguePoints = accountDto.getLeaguePoints();

        if (!accountDto.getScoreResult().equals("")) {
            String scoreResult = accountDto.getScoreResult() + this.score.getResult();
            this.score = new Score(scoreResult.length() < 10 ? scoreResult : scoreResult.substring(0, 10), this);
        }
    }

    public LoLAccount(String summonerId, String puuid, String name, Member user) {
        this.summonerId = summonerId;
        this.puuid = puuid;
        this.name = name;
        this.user = user;
    }
}
