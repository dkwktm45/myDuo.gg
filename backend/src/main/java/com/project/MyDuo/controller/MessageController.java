package com.project.MyDuo.controller;

import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.redis.ChatMessage;
import com.project.MyDuo.security.AuthUser;
import com.project.MyDuo.service.ChatMessageService;
import com.project.MyDuo.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {
	private final ChatService chatService;
	private final ChatMessageService chatMessageService;
	/**
	 * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
	 * roomid 에 따른 메세지 저장?
	 * 매번 룸에 입장을 시킨다는 메세지가 단점이 있다.
	 * 그렇기에 룸에 입장을 한번만 하자!
	 */
	@MessageMapping("/chat/message")
	public void message(ChatMessage message, @ApiIgnore @AuthUser Member member) {
		message.setSender(member.getName());
		chatMessageService.sendChatMessage(message,member);
	}
	/**
	 *
	 * 알람이 올때 어떤 데이터를 알람을 줘야 하는가?
	 * 알람을 선택한다면 해당 채팅방으로 이동이 된다.
	 * 해당 알람은 방에대한 알람이어야 한다.
	 * 보낸 사람한테는 알람이 안간다.
	 *
	 **/
	@DeleteMapping("/message-delete")
	public void deleteMessage(@RequestParam("roomId") String roomId){
		chatMessageService.deleteMessage(roomId);
	}

	@PostMapping("/messages-all")
	@Operation(summary = "findAllMessage", description = "채팅방 Id에 따른 메시지 불러오기")
	public List<ChatMessage> messages(@RequestParam("roomId") String roomId, @AuthUser Member member) {
		return chatMessageService.findAllMessage(roomId);
	}
}
