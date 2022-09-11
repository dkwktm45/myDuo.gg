package com.project.MyDuo.controller;

import com.project.MyDuo.dto.BoardDto;
import com.project.MyDuo.dto.BoardParticipantsDto;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.redis.ChatRoom;
import com.project.MyDuo.security.AuthUser;
import com.project.MyDuo.service.BoardParticipantService;
import com.project.MyDuo.service.ChatService;
import com.project.MyDuo.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class BoardParticipantController {
	private final Logger logger = LoggerFactory.getLogger(BoardParticipantController.class);

	private final BoardParticipantService participantService;
	private final NotificationService notificationService;
	private final BoardParticipantService boardParticipantService;
	private final ChatService chatService;

	@DeleteMapping("/delete")
	@Operation(summary = "게시판 참여자", description = "방장이 해당 유저와 듀오를 원한다면 듀오 결성이 된다.")
	public void deleteRoom(@RequestBody Map<String,Long> info, @AuthUser Member member){
		participantService.deleteRoom(String.valueOf(info.get("boardUuid")),String.valueOf(info.get("participantUuid")),member);
	}

	@PostMapping("/one")
	@Operation(summary = "게시판 참여자", description = "참여자의 정보를 하나 가자온다.")
	public ResponseEntity<BoardParticipantsDto> getOne(@RequestParam("roomId")String roomId,
	                                   @RequestParam("userName")String userName){
		notificationService.deleteAlarm(roomId,userName);
		return ResponseEntity.ok(participantService.findByRoomId(roomId));
	}

	@Operation(summary = "myRoom", description = "내가 만든 게시물에 대한 채팅방 참여자")
	@PostMapping(value = "/my-rooms")
	public ResponseEntity<List<BoardParticipantsDto>> myRoom(Authentication authentication) {
		logger.info("my-room 접근");
		return ResponseEntity.ok(boardParticipantService.myChatRoom(authentication));
	}

	@Operation(summary = "otherRoom", description = "내가 게시물에 참여한 채팅방")
	@PostMapping(value = "/other-rooms")
	public ResponseEntity<List<BoardParticipantsDto>> otherRoom(Authentication authentication) {
		logger.info("other-room 접근");
		return ResponseEntity.ok(boardParticipantService.otherChatRoom(authentication));
	}

	@Operation(summary = "createRoom", description = "게시물에 대해 처음으로 채팅방 참가 할때")
	@PostMapping("/room")
	public Map<String, Object> createRoom(@RequestParam("boardUuid") String boardUuid
			, Authentication authentication) throws Exception {
		logger.info("채팅방 접근");
		ChatRoom chatRoom = chatService.createDuoChat();
		return boardParticipantService.setChat(boardUuid, chatRoom.getRoomId(), authentication);
	}

}
