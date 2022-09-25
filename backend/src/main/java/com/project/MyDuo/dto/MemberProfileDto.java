package com.project.MyDuo.dto;

import com.project.MyDuo.dto.Board.BoardBarDto;
import com.project.MyDuo.dto.LoL.Info.LoLAccountInfoDto;
import com.project.MyDuo.dto.LoL.Info.LoLNameAndPuuidDto;
import lombok.*;

import java.util.List;



@Getter @Setter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfileDto {
    private String userName, userMemo;
    private LoLAccountInfoDto loLAccountInfo;
    private List<BoardBarDto> boardBars;
    private List<LoLNameAndPuuidDto> loLNameAndPuuids;

    private MemberProfileDto(String userName, String userMemo, LoLAccountInfoDto loLAccountInfo, List<BoardBarDto> boardBars, List<LoLNameAndPuuidDto> loLNameAndPuuids) {
        this.userName = userName;
        this.userMemo = userMemo;
        this.loLAccountInfo = loLAccountInfo;
        this.boardBars = boardBars;
        this.loLNameAndPuuids = loLNameAndPuuids;
    }
}
