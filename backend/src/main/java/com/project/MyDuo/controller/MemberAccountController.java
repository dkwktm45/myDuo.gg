package com.project.MyDuo.controller;

import com.project.MyDuo.dto.*;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.security.AuthUser;
import com.project.MyDuo.service.MemberAccountService;
import com.project.MyDuo.service.MemberRepositoryService;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class MemberAccountController {

    private final MemberAccountService memberAccountService;
    private final MemberRepositoryService memberRepositoryService;

    @PutMapping(value = "/heart")
    public void Plus(Authentication authentication ,@RequestBody String roomId){
        memberRepositoryService.heartPlus(authentication,roomId);
    }

    @PostMapping("/join")
    public String join(@RequestBody MemberJoinRequestDto requestDto) throws Exception {
        return memberAccountService.join(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) throws Exception {
        return ResponseEntity.ok(memberAccountService.login(memberLoginRequestDto));
    }

    @GetMapping("/re-issue")
    public ResponseEntity<JwtResponseDto> reIssue(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String refreshToken = authorizationHeader.split(" ")[1];
        return ResponseEntity.ok(memberAccountService.reIssueAccessToken(refreshToken));
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String accessToken = authorizationHeader.split(" ")[1];
        memberAccountService.logout(accessToken);
    }

    @GetMapping("/withdrawal")
    public void withdrawal(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String accessToken = authorizationHeader.split(" ")[1];
        memberAccountService.Withdrawal(accessToken);
    }

    @GetMapping("/profile") @Transactional
    public MemberProfileDto profile(@AuthUser Member member) {
        return memberAccountService.profile(member.getEmail());
    }

    //테스트 용
    @PostMapping("/test")
    public String test(@ApiIgnore @AuthUser Member member) {
        return member.getEmail();
    }
}

