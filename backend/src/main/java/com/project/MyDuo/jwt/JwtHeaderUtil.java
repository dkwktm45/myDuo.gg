package com.project.MyDuo.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtHeaderUtil {

    GRANT_TYPE("JWT 타입 / Bearer ", "Bearer ");

    private final String description;
    private final String value;
}
