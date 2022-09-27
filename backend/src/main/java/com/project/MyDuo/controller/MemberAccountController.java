package com.project.MyDuo.controller;

import com.project.MyDuo.dto.*;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.jwt.JwtTokenUtil;
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

    private final JwtTokenUtil jwtTokenUtil;
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
    public ResponseEntity<JwtResponseDto> reIssue(HttpServletRequest request) {
        String refreshToken = jwtTokenUtil.getToken(request);
        return ResponseEntity.ok(memberAccountService.reIssueAccessToken(refreshToken));
    }

    @GetMapping("/logout")
    public void logout(@ApiIgnore @AuthUser Member member) {
        memberAccountService.logout(member.getEmail());
    }

    @DeleteMapping("/withdrawal")
    public void withdrawal(@ApiIgnore @AuthUser Member member) {
        memberAccountService.Withdrawal(member.getEmail());
    }

    @GetMapping("/profile") @Transactional
    public MemberProfileDto profile(@AuthUser Member member) {
        return memberAccountService.profile(member.getEmail());
    }

    @PatchMapping("/update/password")
    public void UpdatePassword(@ApiIgnore @AuthUser Member member,
                               @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto) {
        memberAccountService.UpdatePassword(member.getId(),updatePasswordRequestDto);
    }

    @PatchMapping("/update/name/{name}")
    public void UpdateName(@ApiIgnore @AuthUser Member member,@PathVariable(name = "name") String name) {
        memberAccountService.UpdateName(member.getId(),name);
    }
}

