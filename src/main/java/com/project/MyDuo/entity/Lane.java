package com.project.MyDuo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
public class Lane {
    @Id @GeneratedValue
    @Column(name = "LANE_ID")
    private Long id;


    
    @Enumerated(EnumType.STRING)
    private LaneType laneType;

    private Integer count;
}
