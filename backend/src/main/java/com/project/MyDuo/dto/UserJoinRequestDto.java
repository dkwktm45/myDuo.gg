package com.project.MyDuo.dto;

import com.project.MyDuo.entity.User;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints .Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class UserJoinRequestDto {

    @Email
    @NotNull
    @Size(min=1, max=20)
    private String email;

    @NotNull
    @Size(min=1, max=20)
    private String name;

    @NotNull
    @Size(min=1, max=200)
    private String password;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();
    }
}