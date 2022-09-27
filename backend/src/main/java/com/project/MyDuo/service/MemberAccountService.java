package com.project.MyDuo.service;

import com.project.MyDuo.dto.*;
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
    private final BoardService boardService;
    private final LoLAccountService loLAccountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;
    private final Mapper mapper;

    public String join(MemberJoinRequestDto requestDto) throws Exception {

        if (memberRepositoryService.findMember(requestDto.getEmail())!=null){
            throw new Exception("이미 해당 이메일로 된 계정이 존재");
        }

        Member member = requestDto.toEntity(passwordEncoder);
        memberRepositoryService.saveMember(member);

        return member.getEmail();
    }

    public JwtResponseDto login(MemberLoginRequestDto requestDto) throws Exception {

        Member member = memberRepositoryService.findMember(requestDto.getEmail());
        if (member == null) {
            throw new NoSuchElementException("해당 이메일이 존재하지 않음");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀림");
        }

        String email = member.getEmail();
        String accessToken = jwtTokenUtil.generateAccessToken(email, member.getRole());
        String refreshToken = refreshTokenService.saveRefreshToken(email, jwtTokenUtil.generateRefreshToken(email, member.getRole()),
                REFRESH_TOKEN_EXPIRATION_TIME.getValue());

        return JwtResponseDto.of(accessToken, refreshToken);
    }

    public void logout(String email) {
        //refreshToken 삭제
        refreshTokenService.deleteRefreshToken(email);

    }

    public void Withdrawal(String email) {

        refreshTokenService.deleteRefreshToken(email);
        memberRepositoryService.deleteMember(email);

    }

    public JwtResponseDto reIssueAccessToken(String token) {

        String email = jwtTokenUtil.getEmail(token);

        Member member = memberRepositoryService.findMember(email);

        if (!token.equals(refreshTokenService.findRefreshToken(email))) {
            throw new NoSuchElementException("토큰이 불일치");
        }

        String accessToken = jwtTokenUtil.generateAccessToken(email, member.getRole());
        String refreshToken = jwtTokenUtil.generateRefreshToken(email, member.getRole());

        refreshTokenService.saveRefreshToken(email, refreshToken, REFRESH_TOKEN_EXPIRATION_TIME.getValue());

        return JwtResponseDto.of(accessToken, refreshToken);
    }

    /*LoLAccount관련 메서드 등록. 작성자: Jeong Seong Soo*/
    @Transactional
    public void addLoLAccount(String email, LoLAccount account) {
        Member user = memberRepositoryService.findMember(email);

        user.addLoLAccount(account);
        account.changeUser(user);
    }
    @Transactional
    public MemberProfileDto profile(String email) {
        Member member = memberRepositoryService.findMember(email);

        return MemberProfileDto
                .builder()
                .userName(member.getName())
                .userMemo(member.getMemo())
                .loLAccountInfo(member.getLoLRepPuuid() == null ?
                        null : mapper.toLoLAccountInfoDto(loLAccountService.findByPuuid(member.getLoLRepPuuid())))
                .boardBars(boardService.getMyBoardBars(member.getEmail()))
                .loLNameAndPuuids(loLAccountService.getSimpleLoLAccountInfos(email))
                .build();
    }
    /*작성자: 게드릉*/
    @Transactional
    public Member headerToEntity(String headers) {
        if(headers == null || headers.equals("null")){
            throw new MessageDeliveryException("메세지 예외");
        }
        String token = headers.substring("Bearer ".length());
        String email = jwtTokenUtil.getEmail(token);
        return memberRepositoryService.findMember(email);
    }

    public void UpdatePassword(Long id, UpdatePasswordRequestDto requestDto) {
        //패스워드가 같은지 비교
        if (!requestDto.getPassword().equals(requestDto.getRepassword())) {
            throw new IllegalArgumentException("입력한 비밀번호가 다릅니다.");
        }
        //같으면 패스워드 암호화 하고 id를 db에 저장
        String password = passwordEncoder.encode(requestDto.getPassword());
        memberRepositoryService.updatePassword(id,password);
    }

    public void UpdateName(Long id, String name) {
        if (name == null) {
            throw new IllegalArgumentException("입력한 이름이 비어있다.");
        }
        memberRepositoryService.updateName(id,name);
    }
}
