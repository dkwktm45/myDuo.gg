package com.project.MyDuo.entity.LoLAccount;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Lane {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lane_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private LaneType lane;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "lol_account_id")
    private LoLAccount loLAccount;

    public Lane(LaneType lane, LoLAccount loLAccount) {
        this.loLAccount = loLAccount;
        this.lane = lane;
        this.count = 0;
    }
}
