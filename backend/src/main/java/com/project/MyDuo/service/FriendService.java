package com.project.MyDuo.service;

import com.project.MyDuo.dao.FriendRepository;
import com.project.MyDuo.dao.MemberRepository;
import com.project.MyDuo.dto.FriendDto;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.Friend;
import com.project.MyDuo.security.CustomUser;
import com.project.MyDuo.service.redis.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {
	private final Logger logger = LoggerFactory.getLogger(FriendService.class);

	private final FriendRepository friendRepository;
	private final ChatRoomRepository roomRepository;
	private final MemberRepository memberRepository;

	public void friendFlus(String email, String roomId) {
		logger.info("friendFlus start");

		Member member = memberRepository.findMemberByEmail(email);
		String friendEmail = roomRepository.findRoomById(roomId).getUserList().stream().filter(info -> !info.equals(email)).collect(Collectors.toList()).get(0);
		Friend friend = Friend.builder().priendEmail(friendEmail).priendUuid(UUID.randomUUID().toString()).member(member).build();

		friendRepository.save(friend);
		logger.info("friendFlus end");
	}

	public List<FriendDto> friendAll( Member member) {
		logger.info("friendAll start");

/*		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Member member = ((CustomUser) userDetails).getMember();*/

		logger.info("friendAll end");
		return friendRepository.findByMember(member).stream().map(FriendDto::new).collect(Collectors.toList());
	}

	public void deleteOne(String uuid,Member member) {
		logger.info("deleteOne start");
		friendRepository.delete(friendRepository.findByMemberAndPriendUuid(member, uuid));
		logger.info("deleteOne end");
	}
}

