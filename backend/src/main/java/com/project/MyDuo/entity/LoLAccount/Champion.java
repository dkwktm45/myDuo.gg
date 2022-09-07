package com.project.MyDuo.entity.LoLAccount;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity @Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Champion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "champion_id")
    private Long id;

    @Column(name = "champion_name")
    private String name;

    @Column(name = "kill_count")
    private Integer kills;
    @Column(name = "death_count")
    private Integer deaths;
    @Column(name = "assist_count")
    private Integer assists;

    @Column(name = "win_count")
    private Integer wins;
    @Column(name = "lose_count")
    private Integer losses;

    @Column(name = "played_time_min")
    private Integer minTime;
    @Column(name = "minion_killed_count")
    private Integer minionKilled;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "lol_account_id")
    private LoLAccount loLAccount;

    public Champion(String name, Integer kills, Integer deaths, Integer assists, Integer wins, Integer losses, Integer minTime, Integer minionKilled, LoLAccount account) {
        this.name = name;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.wins = wins;
        this.losses = losses;
        this.minTime = minTime;
        this.minionKilled = minionKilled;
        this.loLAccount = account;
    }
}
