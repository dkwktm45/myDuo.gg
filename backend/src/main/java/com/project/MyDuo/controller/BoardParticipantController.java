package com.project.MyDuo.controller;

import com.project.MyDuo.entity.BoardParticipants;
import com.project.MyDuo.service.BoardParticipantService;
import com.project.MyDuo.service.NotificationService;
import lombok.RequiredArgsConstructor;
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
	public void deleteRoom(@RequestBody Map<String,Long> info){
		List<BoardParticipants> boardParticipants = participantService.deleteRoom(info.get("boardId"),info.get("participantId"));
	}
	@PostMapping("/one")
	public BoardParticipants getOne(@RequestParam("roomId")String roomId,
	                                @RequestParam("userName")String userName){
		notificationService.deleteAlarm(roomId,userName);
		return participantService.findByRoomId(roomId);
	}
}
