package com.project.MyDuo.entity;

import com.project.MyDuo.dto.MemberJoinRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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

}
