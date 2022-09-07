package com.project.MyDuo.service;

import com.project.MyDuo.dao.MemberRepository;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.security.CustomUser;
import com.project.MyDuo.service.redis.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberRepositoryService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    //public Member findById(Long id){return memberRepository.findById(id);}

    public Member findMember(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    public void deleteMember(String email) {
        memberRepository.deleteMemberByEmail(email);
    }

    /*작성자: Jeong Seong Soo*/
    //public Member findByEmail(String email) { return memberRepository.findByEmail(email);}
    public Member findByBoardList_Uuid(String uuid) {return memberRepository.findByBoardList_Uuid(uuid);}

    public void heartPlus(Authentication authentication, String roomId) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Member member = ((CustomUser) userDetails).getMember();
        member.setHeartPlus();
        String email = chatRoomRepository.findRoomById(roomId).getUserList()
            .stream().filter(info -> !info.equals(member.getEmail())).collect(Collectors.toList()).get(0);

        Member result = memberRepository.findMemberByEmail(email);
        result.setHeartPlus();
        memberRepository.save(result);
    }
}
