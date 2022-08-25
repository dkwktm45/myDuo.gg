package com.project.MyDuo.service;

import com.project.MyDuo.dao.MemberRepository;
import com.project.MyDuo.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberRepositoryService {

    private final MemberRepository memberRepository;

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public Optional<Member> findMember(String email) {
        return memberRepository.findByEmail(email);
    }

    public void deleteMember(String email) {
        memberRepository.deleteByEmail(email);
    }
}
