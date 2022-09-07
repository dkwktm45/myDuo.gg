package com.project.MyDuo.security;

import com.project.MyDuo.entity.Member;
import com.project.MyDuo.service.MemberRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepositoryService memberRepositoryService;

    @Override
    //@Cacheable(value = CacheKey.USER, key = "#email", unless = "#result==null")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepositoryService.findMember(email);
        //todo: null에러 처리
                //.orElseThrow(()-> new NoSuchElementException("등록되지 않은 사용자"));

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));

        return new CustomUser(member,roles);
    }
}
