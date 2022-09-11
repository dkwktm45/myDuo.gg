package com.project.MyDuo.service;

import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.redis.ChatMessage;
import com.project.MyDuo.service.redis.ChatMessageRepository;
import com.project.MyDuo.service.redis.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.project.MyDuo.entity.redis.Alarm.AlarmType.DUO;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
	private final Logger logger = LoggerFactory.getLogger(ChatMessageService.class);

	private final ChatMessageRepository chatMessageRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final NotificationService notificationService;
	private final ChannelTopic channelTopic;
	private final RedisTemplate redisTemplate;

	public void deleteMessage(String roomId){
		List<ChatMessage> chatMessageList = chatMessageRepository.findAllMessage(roomId);
		chatMessageRepository.deleteMessage(chatMessageList);
	}

	public List<ChatMessage> findAllMessage(String roomId) {
		return chatMessageRepository.findAllMessage(roomId);
	}

	/**
	 * 채팅방에 메시지 발송
	 */
	public void sendChatMessage(ChatMessage chatMessage, Member member) {
		if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
			chatMessage.setMessage("님이 방에 입장했습니다.");
			chatMessage.setSender(chatMessage.getSender());
		} else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
			chatMessage.setMessage("님이 방에서 나갔습니다.");
			chatMessage.setSender(chatMessage.getSender());
		}else if (ChatMessage.MessageType.DUO.equals(chatMessage.getType())){

		}
		Long userCount =chatRoomRepository.getUserCount(chatMessage.getRoomId());
		Set<String> RoomUsers = chatRoomRepository.findRoomById(chatMessage.getRoomId()).getUserList();
		try {
			if (userCount.equals(1L) && RoomUsers.size() == 2){
				notificationService.chatType(RoomUsers.stream()
								.filter(info -> !info.equals(member.getEmail()))
								.collect(Collectors.toList()).get(0)
						,chatMessage.getSender(),chatMessage.getRoomId(),DUO);
			}
		}catch (Exception e){
			logger.error("Exception {}", e);
		}
		chatMessageRepository.createChatMessage(chatMessage);
		redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
	}


}
