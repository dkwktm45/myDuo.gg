package com.project.MyDuo.service;

import com.project.MyDuo.dto.JwtResponseDto;
import com.project.MyDuo.dto.UserJoinRequestDto;
import com.project.MyDuo.dto.UserLoginRequestDto;
import com.project.MyDuo.entity.User;
import com.project.MyDuo.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.project.MyDuo.jwt.JwtExpirationEnums.REFRESH_TOKEN_EXPIRATION_TIME;
import static com.project.MyDuo.jwt.JwtExpirationEnums.REISSUE_EXPIRATION_TIME;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserRepositoryService userRepositoryService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;

    public String join(UserJoinRequestDto requestDto) throws Exception {

        if (userRepositoryService.findMember(requestDto.getEmail()).isPresent()){
            throw new Exception("이미 해당 이메일로 된 계정이 존재");
        }

        User user = requestDto.toEntity(passwordEncoder);
        userRepositoryService.saveMember(user);

        return user.getEmail();
    }

    public JwtResponseDto login(UserLoginRequestDto requestDto) throws Exception {

        User user = userRepositoryService.findMember(requestDto.getEmail())
                .orElseThrow(()->new NoSuchElementException("해당 이메일이 존재하지 않음"));

        //비밀번호가 일치하는지
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀림");
        }

        String email = user.getEmail();
        String accessToken = jwtTokenUtil.generateAccessToken(email);
        String refreshToken = refreshTokenService.saveRefreshToken(email, jwtTokenUtil.generateRefreshToken(email),
                REFRESH_TOKEN_EXPIRATION_TIME.getValue());

        return JwtResponseDto.of(accessToken, refreshToken);
    }

    public void logout(String accessToken) {
        //refreshToken 삭제
        String email = jwtTokenUtil.getEmail(accessToken);
        refreshTokenService.deleteRefreshToken(email);

    }

    public void Withdrawal(String accessToken) {
        String email = jwtTokenUtil.getEmail(accessToken);

        /*String token = refreshTokenService.findRefreshToken(email);
        if (!accessToken.equals(token)) {
            throw new NoSuchElementException("토큰이 불일치");
        }*/

        userRepositoryService.deleteMember(email);

    }

    public JwtResponseDto reIssueAccessToken(String refreshToken) {

        String email = jwtTokenUtil.getEmail(refreshToken);

        String token = refreshTokenService.findRefreshToken(email);
        if (!refreshToken.equals(token)) {
            throw new NoSuchElementException("토큰이 불일치");
        }

        String accessToken = jwtTokenUtil.generateAccessToken(email);

        //클라이언트 refresh 토큰의 만료 시간이, 지정한 최소 refresh토큰 만료 시간보다 적을때 새로운 refresh 토큰을 발급해준다.
        if (jwtTokenUtil.getExpirationTime(refreshToken) < REISSUE_EXPIRATION_TIME.getValue()) {
            return JwtResponseDto.of(accessToken, refreshTokenService.saveRefreshToken(email,
                    jwtTokenUtil.generateRefreshToken(email), REFRESH_TOKEN_EXPIRATION_TIME.getValue()));
        }

        return JwtResponseDto.of(accessToken, refreshToken);
    }
}
