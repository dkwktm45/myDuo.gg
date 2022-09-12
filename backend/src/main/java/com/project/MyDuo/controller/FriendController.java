package com.project.MyDuo.controller;

import com.project.MyDuo.dto.FriendDto;
import com.project.MyDuo.entity.Member;
import com.project.MyDuo.entity.redis.Alarm;
import com.project.MyDuo.security.AuthUser;
import com.project.MyDuo.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
	private final Logger logger = LoggerFactory.getLogger(FriendController.class);

	private final FriendService friendService;

	@PutMapping("/agree")
	@Operation(summary = "친구 요청 알림 보내기", description = "친구 추가 반드시 email, roomId를 같이 보내야한다.")
	public void Flus(@RequestParam("email") String email, @RequestParam("roomId") String roomId){
		logger.info("agree start");
		friendService.friendYn(email,roomId);
	}

	@PostMapping("/all")
	@Operation(summary = "친구 목록", description = "나에게 맞는 모든 친구들을 불러온다.")
	public ResponseEntity<List<FriendDto>> all(@ApiIgnore @AuthUser Member member){
		logger.info("all start");
		return ResponseEntity.ok(friendService.friendAll(member));
	}

	@DeleteMapping("/delete-one")
	@Operation(summary = "친구 삭제", description = "친구 삭제에대한 로직")
	public void deleteOne(@RequestParam("friendUuid") String uuid,@ApiIgnore @AuthUser Member member){
		logger.info("deleteOne start");
		friendService.deleteOne(uuid , member);
	}

	@PutMapping("/plus")
	public void friendFlus(@RequestBody Alarm alarm , @ApiIgnore @AuthUser Member member){

		friendService.friendFlus(member.getEmail(),alarm.getId());
//		notificationService.deleteAlarm(roomId,userName);
		logger.info("알림 데이터 : {}",alarm);
	}
}
