package com.project.MyDuo.config.handler;

import com.project.MyDuo.entity.redis.ChatMessage;
import com.project.MyDuo.entity.redis.ChatRoom;
import com.project.MyDuo.service.ChatService;
import com.project.MyDuo.service.redis.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

	//	private final JwtTokenProvider jwtTokenProvider;
	private final ChatService chatService;
	private final ChatRoomRepository chatRoomRepository;

	// websocket을 통해 들어온 요청이 처리 되기전 실행된다.
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		if (StompCommand.SUBSCRIBE == accessor.getCommand()) { // 채팅룸 구독요청
			// header정보에서 구독 destination정보를 얻고, roomId를 추출한다.
			// 나중에 헤더에 유저의 고유값을 가져와야 할듯!!
			// name을 이메일로 변환과정 필요 ui 구현시 바꿈
			String roomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));
			String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");
			ChatRoom chatRoom = chatRoomRepository.findRoomById(roomId);
			if (!chatRoom.getUserList().contains(name) && chatRoom.getUserList().size() < 3) {
				// 클라이언트 입장 메시지를 채팅방에 발송한다.(redis publish)
				log.info("userCount 및 userList 진입");
				chatRoomRepository.plusUserCount(roomId);
				chatRoom.getUserList().add(name);
				chatRoomRepository.updateChatRoom(chatRoom);
				chatService.sendChatMessage(ChatMessage.builder().type(ChatMessage.MessageType.ENTER).roomId(roomId).sender(name).build());
				log.info("SUBSCRIBED {}, {}", name, roomId);
			} else if (chatRoom.getUserList().contains(name) && chatRoomRepository.getUserCount(roomId) <= 3L) {
				chatRoomRepository.plusUserCount(roomId);
			}
		} else if (StompCommand.DISCONNECT == accessor.getCommand()) { // Websocket 연결 종료
			// 연결이 종료된 클라이언트 sesssionId로 채팅방 id를 얻는다.
			Optional<String> roomId = Optional.ofNullable(accessor.getFirstNativeHeader("roomId"));
			if (!roomId.isEmpty()) {
				String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");
				// 채팅방의 인원수를 -1한다.
				chatRoomRepository.minusUserCount(roomId.get());
				// 클라이언트 퇴장 메시지를 채팅방에 발송한다.(redis publish)
				// 퇴장한 클라이언트의 roomId 맵핑 정보를 삭제한다.
				log.info("DISCONNECTED {}, {}", name, roomId);
			}
		}
		return message;
	}
}