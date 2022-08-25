package com.project.MyDuo.service;

import com.project.MyDuo.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRedisService redisService;

    public String saveRefreshToken(String email, String refreshToken, Long expiration) {
        RefreshToken token = RefreshToken.builder()
                .id(email)
                .refreshToken(refreshToken)
                .expiration(expiration/1000)
                .build();

        redisService.setValues(email,refreshToken, Duration.ofMillis(expiration));

        return redisService.getValues(email);
    }

    public String findRefreshToken(String email){
        return redisService.getValues(email);
    }

    public void deleteRefreshToken(String email) {
        redisService.deleteValues(email);
    }
}
