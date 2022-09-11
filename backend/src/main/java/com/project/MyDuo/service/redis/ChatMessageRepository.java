package com.project.MyDuo.service.redis;

import com.project.MyDuo.entity.redis.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatMessageRepository {

	// Redis
	private static final String CHAT_MESSAGE = "CHAT_MESSAGE";
	private static final String CHAT_PARTICIPANTS = "CHAT_PARTICIPANTS";
	private final RedisTemplate<String, Object> redisTemplate;

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, ChatMessage> opsHashChatRoom;

	@PostConstruct
	private void init() {
		opsHashChatRoom = redisTemplate.opsForHash();
	}
	// 모든 채팅 메세지 조회
	public List<ChatMessage> findAllMessage(String id) {
		List<ChatMessage> chatMessages = opsHashChatRoom.values(CHAT_MESSAGE)
				.stream().filter(info -> info.getRoomId().equals(id)).collect(Collectors.toList());
		Collections.sort(chatMessages,Collections.reverseOrder());
		return chatMessages;
	}
	public void deleteMessage(List<ChatMessage> chatMessageList){
		opsHashChatRoom.delete(CHAT_MESSAGE,chatMessageList);
	}
	// 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
	public void createChatMessage(ChatMessage message) {
		message.setMessageId(UUID.randomUUID().toString());
		opsHashChatRoom.put(CHAT_MESSAGE, message.getMessageId(), message);
	}
}

