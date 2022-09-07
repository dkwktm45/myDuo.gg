package com.project.MyDuo.dto.Board;

import com.project.MyDuo.dto.LoL.Info.LoLAccountInfoDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardDetailDto {
    String userName, boardContent;
    Long userHeart;
    LoLAccountInfoDto loLAccountInfoDto;
    boolean micEnabled;

    public BoardDetailDto(String userName, String boardContent, Long userHeart, LoLAccountInfoDto loLAccountInfoDto, boolean micEnabled) {
        this.userName = userName;
        this.boardContent = boardContent;
        this.userHeart = userHeart;
        this.loLAccountInfoDto = loLAccountInfoDto;
        this.micEnabled = micEnabled;
    }
}
