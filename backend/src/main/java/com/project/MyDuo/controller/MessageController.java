package com.project.MyDuo.controller;

import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.redis.ChatMessage;
import com.project.MyDuo.jwt.JwtTokenUtil;
import com.project.MyDuo.security.AuthUser;
import com.project.MyDuo.service.ChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {
	private final ChatMessageService chatMessageService;
	private final JwtTokenUtil jwtTokenUtil;

	@MessageMapping("/chat/message")
	public void message(ChatMessage message, @Header("Authorization") String token) {
		chatMessageService.sendChatMessage(message, jwtTokenUtil
				.getEmail(token.substring("Bearer ".length())));
	}

	@DeleteMapping("/message-delete")
	public void deleteMessage(@RequestParam("roomId") String roomId) {
		chatMessageService.deleteMessage(roomId);
	}

	@PostMapping("/messages-all")
	@Operation(summary = "findAllMessage", description = "채팅방 Id에 따른 메시지 불러오기")
	public List<ChatMessage> messages(@RequestParam("roomId") String roomId, @AuthUser Member member) {
		return chatMessageService.findAllMessage(roomId);
	}
}
