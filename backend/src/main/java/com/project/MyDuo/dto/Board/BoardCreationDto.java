package com.project.MyDuo.dto.Board;

import com.project.MyDuo.entity.LoLAccount.LaneType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BoardCreationDto {
    private String lolPuuid;
    List<LaneType> myPositions, otherPositions;
    boolean micEnabled;
    String content;
}
