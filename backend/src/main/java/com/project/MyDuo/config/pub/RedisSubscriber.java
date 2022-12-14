package com.project.MyDuo.config.pub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.project.MyDuo.config.converter.LocalDateTimeSerializer;
import com.project.MyDuo.entity.redis.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {

	private final SimpMessageSendingOperations messagingTemplate;
	/**
	 * Redis에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber가 해당 메시지를 받아 처리한다.
	 */
	public void sendMessage(String publishMessage) {
		try {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).create();

			// ChatMessage 객채로 맵핑
			ChatMessage chatMessage = gson.fromJson(publishMessage, ChatMessage.class);
			// 채팅방을 구독한 클라이언트에게 메시지 발송
			messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
		} catch (Exception e) {
			log.error("Exception {}", e);
		}
	}
}