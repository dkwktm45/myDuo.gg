package com.project.MyDuo.dao;

import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

	List<Friend> findByMember(Member member);

	Friend findByMemberAndPriendUuid(Member member, String uuid);
}
