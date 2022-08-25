package com.project.MyDuo.config.handler;

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

	/*private final JwtTokenProvider jwtTokenProvider;
	private final ChatService chatService;
	private final ChatRoomRepository chatRoomRepository;
	private final BoardParticipantService participantsService;
	private final BoardParticipantsRepository participantsRepository;
*/	// websocket을 통해 들어온 요청이 처리 되기전 실행된다.
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		/*StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		if (StompCommand.CONNECT == accessor.getCommand()) { // websocket 연결요청
			String jwtToken = accessor.getFirstNativeHeader("token");
			log.info("CONNECT {}", jwtToken);
			// Header의 jwt token 검증
			jwtTokenProvider.validateToken(jwtToken);
		} else if (StompCommand.SUBSCRIBE == accessor.getCommand()) { // 채팅룸 구독요청
			// header정보에서 구독 destination정보를 얻고, roomId를 추출한다.
			String roomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));
			String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");

			BoardParticipants participants =participantsRepository.findByRoomId(roomId);
			if(!participants.getUserList().contains(name) && participants.getUserList().size() < 3){
				// 클라이언트 입장 메시지를 채팅방에 발송한다.(redis publish)
				log.info("userCount 및 userList 진입");
				chatRoomRepository.plusUserCount(roomId);
				participants.getUserList().add(name);
				participantsService.updateUserList(participants);
				chatService.sendChatMessage(ChatMessage.builder().type(ChatMessage.MessageType.ENTER).roomId(roomId).sender(name).build());
				log.info("SUBSCRIBED {}, {}", name, roomId);
			}else if(participants.getUserList().contains(name) && chatRoomRepository.minusUserCount(roomId) == 1L){
				chatRoomRepository.plusUserCount(roomId);
			}
		}else if (StompCommand.DISCONNECT == accessor.getCommand()) { // Websocket 연결 종료
			// 연결이 종료된 클라이언트 sesssionId로 채팅방 id를 얻는다.
			String roomId = accessor.getFirstNativeHeader("roomId");
			String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");
			// 채팅방의 인원수를 -1한다.
			chatRoomRepository.minusUserCount(roomId);
			// 클라이언트 퇴장 메시지를 채팅방에 발송한다.(redis publish)
			// 퇴장한 클라이언트의 roomId 맵핑 정보를 삭제한다.
			log.info("DISCONNECTED {}, {}", name, roomId);
		}*/
		return message;
	}
}