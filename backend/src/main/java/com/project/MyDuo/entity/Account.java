package com.project.MyDuo.entity;

import com.project.MyDuo.dto.AccountDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
    public Account(AccountDto accountDto){
        this.id = accountDto.getId();
        this.name = accountDto.getName();
        this.email = accountDto.getEmail();
        this.password = accountDto.getPassword();
        this.heart = accountDto.getHeart();
        if(accountDto.getBoardDtoList() == null){
            this.boardList = null;
        }else{
            this.boardList = new ArrayList(accountDto.getBoardDtoList());
        }
    }
    @OneToMany(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private List<Board> boardList;
}
