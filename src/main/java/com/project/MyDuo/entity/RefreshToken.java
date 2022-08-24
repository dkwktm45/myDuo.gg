package com.project.MyDuo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@RedisHash("refreshToken")
public class RefreshToken {

    @Id
    private String id;

    @NotNull
    private String refreshToken;

    @TimeToLive
    @NotNull
    private Long expiration;

    @Builder
    public RefreshToken(String id, String refreshToken, Long expiration) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
