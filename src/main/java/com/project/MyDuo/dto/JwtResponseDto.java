package com.project.MyDuo.dto;

import com.project.MyDuo.jwt.JwtHeaderUtilEnums;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtResponseDto {

    private final String grantType;

    private final String accessToken;

    private final String refreshToken;

    @Builder
    public JwtResponseDto(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static JwtResponseDto of(String accessToken, String refreshToken) {
        return JwtResponseDto.builder()
                .grantType(JwtHeaderUtilEnums.GRANT_TYPE.getValue())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
