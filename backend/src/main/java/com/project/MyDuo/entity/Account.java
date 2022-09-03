package com.project.MyDuo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Embedded
    @Column(name = "user_email", length = 100)
    private String email;

    @NotNull
    @Column(name = "user_name", length = 100)
    private String name;

    @NotNull
    @Column(name = "user_pwd", length = 200)
    private String password;

    @NotNull
    @Column(name = "user_heart")
    private int heart;

    @NotNull
    @Column(name = "user_valid")
    private Boolean valid;

    private Role role;
    @Builder
    public Account(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        heart=0;
        valid=true;
        role=Role.USER;
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
   /* @OneToMany(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private List<Board> boardList;*/

}
