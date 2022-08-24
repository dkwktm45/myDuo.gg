package com.project.MyDuo.controller;

import com.project.MyDuo.dto.JwtResponseDto;
import com.project.MyDuo.dto.MemberJoinRequestDto;
import com.project.MyDuo.dto.MemberLoginRequestDto;
import com.project.MyDuo.service.MemberAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class MemberAccountController {

    private final MemberAccountService memberAccountService;

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
        String refreshToken = authorizationHeader.split(" ")[1];
        memberAccountService.logout(refreshToken);
    }


    @GetMapping("/withdrawal")
    public void withdrawal(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String refreshToken = authorizationHeader.split(" ")[1];
        memberAccountService.Withdrawal(refreshToken);
    }
}
