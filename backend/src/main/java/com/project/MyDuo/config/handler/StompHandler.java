package com.project.MyDuo.config.handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.MyDuo.entity.redis.ChatMessage;
import com.project.MyDuo.entity.redis.ChatRoom;
import com.project.MyDuo.jwt.JwtTokenUtil;
import com.project.MyDuo.service.ChatMessageService;
import com.project.MyDuo.service.ChatService;
import com.project.MyDuo.service.MemberAccountService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

	private final ChatService chatService;
	private final ChatMessageService messageService;
	private final JwtTokenUtil jwtTokenUtil;
	private final MemberAccountService accountService;

	// websocket을 통해 들어온 요청이 처리 되기전 실행된다.
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		if (StompCommand.SUBSCRIBE == accessor.getCommand()) { // 채팅룸 구독요청
			// 헤더 값 추출
			String authorizationHeader = String.valueOf(accessor.getNativeHeader("Authorization"));
			String nickName = String.valueOf(accessor.getNativeHeader("name"));
			String email = jwtTokenUtil.getEmail(authorizationHeader.substring("Bearer ".length()));
			String roomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));
			ChatRoom chatRoom = chatService.findRoom(roomId);

			if (!chatRoom.getUserList().contains(email) && chatRoom.getUserList().size() < 3) {
				// 클라이언트 입장 메시지를 채팅방에 발송한다.(redis publish)
				log.info("userCount 및 userList 진입");
				chatService.plusCount(roomId);
				chatRoom.getUserList().add(email);
				chatService.updateRoom(chatRoom);
				messageService.sendChatMessage(ChatMessage.builder().type(ChatMessage.MessageType.ENTER).roomId(roomId).sender(nickName).build(), email);
				log.info("SUBSCRIBED {}, {}", email, roomId);
			} else if (chatRoom.getUserList().contains(email) && chatService.infoCount(roomId) <= 3L) {
				chatService.plusCount(roomId);
			}
		} else if (StompCommand.DISCONNECT == accessor.getCommand()) { // Websocket 연결 종료
			// 연결이 종료된 클라이언트 sesssionId로 채팅방 id를 얻는다.
			Optional<String> roomId = Optional.ofNullable(accessor.getFirstNativeHeader("roomId"));
			if (!roomId.isEmpty()) {
				chatService.minusCount(roomId.get());
				log.info("DISCONNECTED {}", roomId);
			}
		}
		return message;
	}

}