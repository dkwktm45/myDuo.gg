package com.project.MyDuo.service;

import com.project.MyDuo.dto.JwtResponseDto;
import com.project.MyDuo.dto.MemberJoinRequestDto;
import com.project.MyDuo.dto.MemberLoginRequestDto;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.LoLAccount.LoLAccount;
import com.project.MyDuo.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.project.MyDuo.jwt.JwtExpiration.REFRESH_TOKEN_EXPIRATION_TIME;
import static com.project.MyDuo.jwt.JwtExpiration.REISSUE_EXPIRATION_TIME;

@Service
@RequiredArgsConstructor
public class MemberAccountService {

    private final MemberRepositoryService memberRepositoryService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;

    public String join(MemberJoinRequestDto requestDto) throws Exception {

        /*if (memberRepositoryService.findMember(requestDto.getEmail()).isPresent()){
            throw new Exception("이미 해당 이메일로 된 계정이 존재");
        }*/

        Member member = requestDto.toEntity(passwordEncoder);
        memberRepositoryService.saveMember(member);

        return member.getEmail();
    }

    public JwtResponseDto login(MemberLoginRequestDto requestDto) throws Exception {

        Member member = memberRepositoryService.findMember(requestDto.getEmail());
               // .orElseThrow(()->new NoSuchElementException("해당 이메일이 존재하지 않음"));

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀림");
        }

        String email = member.getEmail();
        String accessToken = jwtTokenUtil.generateAccessToken(email, member.getRole());
        String refreshToken = refreshTokenService.saveRefreshToken(email, jwtTokenUtil.generateRefreshToken(email, member.getRole()),
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

        memberRepositoryService.deleteMember(email);

    }

    public JwtResponseDto reIssueAccessToken(String refreshToken) {

        String email = jwtTokenUtil.getEmail(refreshToken);

        Member member = memberRepositoryService.findMember(email);
                //.orElseThrow(()->new NoSuchElementException("해당 이메일이 존재하지 않음"));

        String token = refreshTokenService.findRefreshToken(email);
        if (!refreshToken.equals(token)) {
            throw new NoSuchElementException("토큰이 불일치");
        }

        String accessToken = jwtTokenUtil.generateAccessToken(email, member.getRole());

        //클라이언트 refresh 토큰의 만료 시간이, 지정한 최소 refresh토큰 만료 시간보다 적을때 새로운 refresh 토큰을 발급해준다.
        if (jwtTokenUtil.getExpirationTime(refreshToken) < REISSUE_EXPIRATION_TIME.getValue()) {
            return JwtResponseDto.of(accessToken, refreshTokenService.saveRefreshToken(email,
                    jwtTokenUtil.generateRefreshToken(email, member.getRole()), REFRESH_TOKEN_EXPIRATION_TIME.getValue()));
        }

        return JwtResponseDto.of(accessToken, refreshToken);
    }

    /*LoLAccount관련 메서드 등록. 작성자: Jeong Seong Soo*/
    @Transactional
    public void addLoLAccount(String email, LoLAccount account) {
        Member user = memberRepositoryService.findMember(email);

        user.addLoLAccount(account);
        account.changeUser(user);
    }

    /*작성자: 게드릉*/
    @Transactional
    public Member headrToEntity(String headers) {
        if(headers == null || headers.equals("null")){
            throw new MessageDeliveryException("메세지 예외");
        }
        String token = headers.substring("Bearer ".length());
        String email = jwtTokenUtil.getEmail(token);
        return memberRepositoryService.findMember(email);
    }
}
