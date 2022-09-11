package com.project.MyDuo.dto.Board;

import lombok.Data;

@Data
public class BoardDetailBaseDto {
    private Boolean micEnabled;
    private String content;
    private String lolPuuid;

    private String userName;
    private String email;
    private Integer heart;
    private Boolean userValidStatus;

    public BoardDetailBaseDto(Boolean micEnabled, String content, String lolPuuid, String userName, String email, Integer heart, Boolean userValidStatus) {
        this.micEnabled = micEnabled;
        this.content = content;
        this.lolPuuid = lolPuuid;
        this.userName = userName;
        this.email = email;
        this.heart = heart;
        this.userValidStatus = userValidStatus;
    }
}
