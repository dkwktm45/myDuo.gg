package com.project.MyDuo.controller;

import com.project.MyDuo.dto.BoardDto;
import com.project.MyDuo.dto.BoardParticipantsDto;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.redis.ChatMessage;
import com.project.MyDuo.entity.redis.ChatRoom;
import com.project.MyDuo.security.AuthUser;
import com.project.MyDuo.service.BoardParticipantService;
import com.project.MyDuo.service.ChatService;
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

	private final ChatMessageRepository chatMessageRepository;
	private final ChatService chatService;


	@DeleteMapping("/messages")
	public void deleteRoom(@RequestParam("roomId") String roomId){
		chatService.deleteRoom(roomId);
	}

	@Operation(summary = "friendCreate", description = "채팅방에 유저가 없다면 생성한다.")
	@PostMapping("/friend-create")
	public void friendCreate(@RequestBody String email,@AuthUser Member member){
		chatService.createFriendChat(member,email);
	}
}