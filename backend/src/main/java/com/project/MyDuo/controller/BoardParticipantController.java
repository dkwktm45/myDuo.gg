package com.project.MyDuo.controller;

import com.project.MyDuo.dto.BoardParticipantsDto;
import com.project.MyDuo.entity.BoardParticipants;
import com.project.MyDuo.service.BoardParticipantService;
import com.project.MyDuo.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class BoardParticipantController {
	private final BoardParticipantService participantService;
	private final NotificationService notificationService;

	@DeleteMapping("/delete")
	@Operation(summary = "게시판 참여자", description = "방장이 해당 유저와 듀오를 원한다면 듀오 결성이 된다.")
	public void deleteRoom(@RequestBody Map<String,Long> info){
		participantService.deleteRoom(String.valueOf(info.get("boardUuid")),String.valueOf(info.get("participantUuid")));
	}

	@PostMapping("/one")
	@Operation(summary = "게시판 참여자", description = "참여자의 정보를 하나 가자온다.")
	public ResponseEntity<BoardParticipantsDto> getOne(@RequestParam("roomId")String roomId,
	                                   @RequestParam("userName")String userName){
		notificationService.deleteAlarm(roomId,userName);
		return ResponseEntity.ok(participantService.findByRoomId(roomId));
	}
}
