package com.project.MyDuo.controller;

import com.project.MyDuo.entity.redis.ChatMessage;
import com.project.MyDuo.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {
//	private final JwtTokenProvider jwtTokenProvider;
	private final ChatService chatService;
	/**
	 * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
	 * roomid 에 따른 메세지 저장?
	 * 매번 룸에 입장을 시킨다는 메세지가 단점이 있다.
	 * 그렇기에 룸에 입장을 한번만 하자!
	 */
	@MessageMapping("/chat/message")
	public void message(ChatMessage message, @Header("token") String token) {
		/*String nickname = jwtTokenProvider.getUserNameFromJwt(token);
		// 로그인 회원 정보로 대화명 설정
		message.setSender(nickname);
		// Websocket에 발행된 메시지를 redis로 발행(publish)
		chatService.sendChatMessage(message);*/
	}
	/**
	 *
	 * 알람이 올때 어떤 데이터를 알람을 줘야 하는가?
	 * 알람을 선택한다면 해당 채팅방으로 이동이 된다.
	 * 해당 알람은 방에대한 알람이어야 한다.
	 * 보낸 사람한테는 알람이 안간다.
	 *
	 **/

}
