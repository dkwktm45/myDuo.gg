package com.project.MyDuo.security;

import com.project.MyDuo.entity.Account;
import com.project.MyDuo.security.CustomUser;
import com.project.MyDuo.service.UserRepositoryService;
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

    private final UserRepositoryService userRepositoryService;

    @Override
    //@Cacheable(value = CacheKey.USER, key = "#email", unless = "#result==null")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = userRepositoryService.findMember(email)
                .orElseThrow(()-> new NoSuchElementException("등록되지 않은 사용자"));

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + account.getRole().name()));

        return new CustomUser(account,roles);
    }
}
