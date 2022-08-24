package com.project.MyDuo.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class MemberLoginRequestDto {

    @Email
    @NotNull
    @Size(min=1, max=20)
    private String email;

    @NotNull
    @Size(min=1, max=200)
    private String password;
}
