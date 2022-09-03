package com.project.MyDuo.controller;

import com.project.MyDuo.entity.Board;
import com.project.MyDuo.entity.BoardParticipants;
import com.project.MyDuo.entity.redis.ChatMessage;
import com.project.MyDuo.entity.redis.ChatRoom;
import com.project.MyDuo.service.BoardParticipantService;
import com.project.MyDuo.service.redis.ChatMessageRepository;
import com.project.MyDuo.service.redis.ChatRoomRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {
	private final Logger logger = LoggerFactory.getLogger(ChatRoomController.class);
	private final ChatRoomRepository chatRoomRepository;
	private final ChatMessageRepository chatMessageRepository;
//	private final JwtTokenProvider jwtTokenProvider;
	private final BoardParticipantService boardParticipantService;

	@Operation(summary = "myRoom", description = "내가 만든 게시물에 대한 채팅방 참여자")
	@PostMapping(value = "/my-rooms")
	public ResponseEntity<List<BoardParticipants>> myRoom(@RequestBody Long userId) {
		logger.info("my-room 접근");
		return ResponseEntity.ok(boardParticipantService.myChatRoom(userId));
	}

	@Operation(summary = "otherRoom", description = "내가 게시물에 참여한 채팅방")
	@PostMapping(value = "/other-rooms")
	public ResponseEntity<List<Board>> otherRoom(@RequestBody Long userId) {
		logger.info("other-room 접근");
		return ResponseEntity.ok(boardParticipantService.otherChatRoom(userId));
	}

	@PostMapping("/messages")
	@Operation(summary = "findAllMessage", description = "채팅방 Id에 따른 메시지 불러오기")
	public List<ChatMessage> messages(@RequestParam("roomId") String id) {
		return chatMessageRepository.findAllMessage(id);
	}

	@Operation(summary = "createRoom", description = "게시물에 대해 처음으로 채팅방 참가 할때")
	@PostMapping("/room")
	public Map<String, Object> createRoom(@RequestParam(value = "boardId",required=false) Long boardId
			, @RequestParam("userName") String userName, @RequestParam("userId") Long userId) throws Exception {
		logger.info("채팅방 접근");
		ChatRoom chatRoom = chatRoomRepository.createChatRoom();
		return boardParticipantService.setChat(boardId,chatRoom.getRoomId() ,userId ,userName);
	}
/*	@GetMapping("/user")
	public LoginInfo getUserInfo() {
		logger.info("user token 발급");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return LoginInfo.builder().name(name).token(jwtTokenProvider.generateToken(name)).build();
	}*/
}