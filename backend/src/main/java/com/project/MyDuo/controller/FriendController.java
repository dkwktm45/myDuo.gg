package com.project.MyDuo.controller;

import com.project.MyDuo.dto.FriendDto;
import com.project.MyDuo.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
	private final Logger logger = LoggerFactory.getLogger(FriendController.class);

	private final FriendService friendService;

	@PutMapping("/plus")
	@Operation(summary = "친구 추가", description = "친구 추가 반드시 email, roomId를 같이 보내야한다.")
	public void Flus(@RequestParam("email") String email, @RequestParam("roomId") String roomId){
		logger.info("plus start");
		friendService.friendFlus(email,roomId);
	}
	@PostMapping("/all")
	@Operation(summary = "친구 목록", description = "나에게 맞는 모든 친구들을 불러온다.")
	public ResponseEntity<List<FriendDto>> all(Authentication authentication){
		logger.info("all start");
		return ResponseEntity.ok(friendService.friendAll(authentication));
	}
	@DeleteMapping("/one")
	@Operation(summary = "친구 목록", description = "나에게 맞는 모든 친구들을 불러온다.")
	public void deleteOne(@RequestParam("friendUuid") String uuid,Authentication authentication){
		logger.info("deleteOne start");
		friendService.deleteOne(uuid , authentication);
	}

}
