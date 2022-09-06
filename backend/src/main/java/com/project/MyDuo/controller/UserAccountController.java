package com.project.MyDuo.controller;

import com.project.MyDuo.dto.JwtResponseDto;
import com.project.MyDuo.dto.UserJoinRequestDto;
import com.project.MyDuo.dto.UserLoginRequestDto;
import com.project.MyDuo.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/join")
    public String join(@RequestBody UserJoinRequestDto requestDto) throws Exception {
        return userAccountService.join(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto) throws Exception {
        return ResponseEntity.ok(userAccountService.login(userLoginRequestDto));
    }

    @GetMapping("/re-issue")
    public ResponseEntity<JwtResponseDto> reIssue(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String refreshToken = authorizationHeader.split(" ")[1];
        return ResponseEntity.ok(userAccountService.reIssueAccessToken(refreshToken));
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String accessToken = authorizationHeader.split(" ")[1];
        userAccountService.logout(accessToken);
    }


    @GetMapping("/withdrawal")
    public void withdrawal(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String accessToken = authorizationHeader.split(" ")[1];
        userAccountService.Withdrawal(accessToken);
    }
}

