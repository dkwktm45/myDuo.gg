package com.project.MyDuo.service;

import com.project.MyDuo.entity.redis.ChatMessage;
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

}
