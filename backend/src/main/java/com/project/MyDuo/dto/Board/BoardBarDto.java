package com.project.MyDuo.dto.Board;

import com.project.MyDuo.entity.LoLAccount.LaneType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class BoardBarDto {
    private Boolean closingStatus;
    private String summonerName, tier, rank;
    private List<Integer> myPositions, otherPositions;
    private String content;
    private Timestamp registrationTime;
    private String boardUuid;

    public BoardBarDto(Boolean closingStatus, String summonerName, String tier, String rank, List<LaneType> myPositions, List<LaneType> otherPositions, String content, Date registrationTime, String boardUuid) {
        this.closingStatus = closingStatus;
        this.summonerName = summonerName;
        this.tier = tier;
        this.rank = rank;
        this.myPositions = toIntegerList(myPositions);
        this.otherPositions = toIntegerList(otherPositions);
        this.content = content;
        this.registrationTime = new Timestamp(registrationTime.getTime());
        this.boardUuid = boardUuid;
    }

    private List<Integer> toIntegerList(List<LaneType> positions) {
        return new ArrayList<>(){{
            add(positions.get(0).ordinal());
            add(positions.get(1).ordinal());
        }};
    }
}
