package com.project.MyDuo.service;

import com.project.MyDuo.dao.FriendRepository;
import com.project.MyDuo.dao.MemberRepository;
import com.project.MyDuo.dto.FriendDto;
import com.project.MyDuo.entity.Friend;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.service.redis.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.project.MyDuo.entity.redis.Alarm.AlarmType.AGREE;
import static com.project.MyDuo.entity.redis.Alarm.AlarmType.MESSAGE;

@Service
@RequiredArgsConstructor
public class FriendService {
	private final Logger logger = LoggerFactory.getLogger(FriendService.class);

	private final FriendRepository friendRepository;
	private final ChatRoomRepository roomRepository;
	private final MemberRepository memberRepository;
	private final NotificationService notificationService;

	public void friendYn(String email, String roomId) {
		logger.info("friendYn start");

		Member member = memberRepository.findMemberByEmail(email);
		String friendEmail = roomRepository.findRoomById(roomId).getUserList().stream().filter(info -> !info.equals(email)).collect(Collectors.toList()).get(0);
		notificationService.agreeTyp(member.getEmail(),friendEmail,AGREE);
		logger.info("friendYn end");

	}

	public List<FriendDto> friendAll( Member member) {
		return friendRepository.findByMember(member).stream().map(FriendDto::new).collect(Collectors.toList());
	}

	public void deleteOne(String uuid,Member member) {
		logger.info("deleteOne start");
		friendRepository.delete(friendRepository.findByMemberAndPriendUuid(member, uuid));
		logger.info("deleteOne end");
	}

	public void friendFlus(String senderName, String receveName) {
		Member recevUser = memberRepository.findMemberByEmail(receveName);

		Member sendUser = memberRepository.findMemberByEmail(senderName);
		friendRepository.save(Friend.builder().member(recevUser)
				.priendUuid(UUID.randomUUID().toString())
				.priendEmail(sendUser.getEmail()).build());
		friendRepository.save(Friend.builder().member(sendUser)
				.priendUuid(UUID.randomUUID().toString())
				.priendEmail(recevUser.getEmail()).build());

		notificationService.agreeTyp(sendUser.getEmail(),recevUser.getEmail(),MESSAGE);
		logger.info("friendFlus end");
	}
}

