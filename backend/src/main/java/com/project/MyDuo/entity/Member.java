package com.project.MyDuo.entity;

import com.project.MyDuo.dto.MemberJoinRequestDto;
import com.project.MyDuo.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @NotNull
    @Column(length = 20)
    private String email;

    @NotNull
    @Column(length = 20)
    private String name;

    @NotNull
    @Column(length = 200)
    private String password;

    @Builder
    public Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
/*
    public Member(UserDto user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.userPwd = user.getUserPwd();
        this.userHeart = user.getUserHeart();
    }
*/
    @OneToMany(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private List<Board> boardList;

}
