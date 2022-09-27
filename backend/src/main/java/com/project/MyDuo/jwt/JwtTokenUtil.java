package com.project.MyDuo.jwt;

import com.project.MyDuo.entity.Role;
import com.project.MyDuo.security.error.exception.ErrorCode;
import com.project.MyDuo.security.error.exception.NotValidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

import static com.project.MyDuo.jwt.JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME;
import static com.project.MyDuo.jwt.JwtExpiration.REFRESH_TOKEN_EXPIRATION_TIME;

@Slf4j
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(String email, Role role) {
        return Jwts.builder()
                .setSubject(TokenType.ACCESS.name())
                .setAudience(email)
                .setIssuedAt(new Date())
                .setExpiration(createAccessTokenExpireTime())
                .claim("role", role)
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    private Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME.getValue());
    }

    public String generateRefreshToken(String email, Role role) {
        return Jwts.builder()
                .setSubject(TokenType.REFRESH.name())
                .setAudience(email)
                .setIssuedAt(new Date())
                .setExpiration(creatRefreshTokenExpireTime())
                .claim("role", role)
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    private Date creatRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME.getValue());
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmail(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getAudience();
        } catch (Exception e) {
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token);
        } catch (MalformedJwtException | SecurityException e) {
            log.error("Wrong jwt token");
            throw new NotValidTokenException(ErrorCode.INVALID_TOKEN_SIGNATURE);
        } catch (ExpiredJwtException e) {
            log.error("expired jwt token");
            throw new NotValidTokenException(ErrorCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("unsupported jwt token");
            throw new NotValidTokenException(ErrorCode.INVALID_TOKEN_SIGNATURE);
        } catch (IllegalArgumentException e) {
            log.error("Wrong jwt token");
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }
    }

    public String getToken(HttpServletRequest request) {

        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
