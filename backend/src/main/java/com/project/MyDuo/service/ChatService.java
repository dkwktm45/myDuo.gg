package com.project.MyDuo.service;

import com.project.MyDuo.dao.FriendRepository;
import com.project.MyDuo.dao.MemberRepository;
import com.project.MyDuo.dto.FriendDto;
import com.project.MyDuo.entity.Friend;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.redis.ChatMessage;
import com.project.MyDuo.entity.redis.ChatRoom;
import com.project.MyDuo.service.redis.ChatMessageRepository;
import com.project.MyDuo.service.redis.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.MyDuo.entity.redis.Alarm.AlarmType.DUO;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
	private final Logger logger = LoggerFactory.getLogger(ChatService.class);
	private final ChannelTopic channelTopic;
	private final RedisTemplate redisTemplate;

	private final ChatRoomRepository chatRoomRepository;
	private final NotificationService notificationService;
	private final ChatMessageRepository chatMessageRepository;
	private final FriendRepository friendRepository;
	private final MemberRepository memberRepository;
	/**
	 * destination정보에서 roomId 추출
	 */
	public String getRoomId(String destination) {
		int lastIndex = destination.lastIndexOf('/');
		if (lastIndex != -1)
			return destination.substring(lastIndex + 1);
		else
			return "";
	}
	/**
	 * 채팅방에 메시지 발송
	 */
	public void sendChatMessage(ChatMessage chatMessage) {
		if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
			chatMessage.setMessage("님이 방에 입장했습니다.");
			chatMessage.setSender(chatMessage.getSender());
		} else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
			chatMessage.setMessage("님이 방에서 나갔습니다.");
			chatMessage.setSender(chatMessage.getSender());
		}else if (ChatMessage.MessageType.DUO.equals(chatMessage.getType())){

		}
		Long userCount =chatRoomRepository.getUserCount(chatMessage.getRoomId());
		chatMessage.setCreatedAt(LocalDateTime.now());
		Set<String> RoomUsers = chatRoomRepository.findRoomById(chatMessage.getRoomId()).getUserList();
		try {
			if (userCount.equals(1L) && RoomUsers.size() == 2){
				notificationService.sendMessage(RoomUsers.stream()
								.filter(info -> !info.equals(chatMessage.getSender()))
								.collect(Collectors.toList()).get(0)
						,chatMessage.getSender(),chatMessage.getRoomId(),DUO);
			}
		}catch (Exception e){
			log.error("Exception {}", e);
		}
		chatMessageRepository.createChatMessage(chatMessage);
		redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
	}

	public void createFriendChat(Member member, String email) {

		ChatRoom chatRoom = ChatRoom.create();
		Set<String> userList = new HashSet<>();
		userList.add(email);
		userList.add(member.getEmail());
		chatRoom.setUserList(userList);
		chatRoomRepository.save(chatRoom);

		Friend friend = friendRepository.findByMemberAndPriendEmail(member,email);
		friend.toSetRoomId(chatRoom.getRoomId());
		friendRepository.save(friend);

		Friend otherFriend = memberRepository.findMemberByEmail(email).getFriends()
				.stream().filter(info -> info.getPriendEmail().equals(member.getEmail())).findFirst()
				.orElseThrow(() -> new IllegalArgumentException());

		otherFriend.toSetRoomId(chatRoom.getRoomId());
	}

	/**
	 * Chat
	 * 간단 로직 함수
	 */
	public ChatRoom createDuoChat(){
		return chatRoomRepository.createChatRoom();
	}

	public void deleteRoom(String chatRoomId) {
		chatRoomRepository.deleteRoom(chatRoomId);
	}

	public ChatRoom findRoom(String roomId){return chatRoomRepository.findRoomById(roomId);}

	public void updateRoom(ChatRoom chatRoom){
		chatRoomRepository.updateChatRoom(chatRoom);
	}

	public long plusCount(String roomId) {return chatRoomRepository.plusUserCount(roomId);}

	public long minusCount(String roomId) {return chatRoomRepository.minusUserCount(roomId);}

}
