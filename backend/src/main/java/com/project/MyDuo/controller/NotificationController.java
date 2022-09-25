package com.project.MyDuo.controller;

import com.project.MyDuo.entity.redis.Alarm;
import com.project.MyDuo.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://127.0.0.1:8012")
public class NotificationController {

	private final NotificationService notificationService;
	private static final Map<String, SseEmitter> CLIENTS = new ConcurrentHashMap<>();


	@GetMapping(value = "/receive/notify/{userId}", produces = "text/event-stream")
	public SseEmitter receiveNotify(@PathVariable("userId") String userId) {
		SseEmitter sseEmitter = new SseEmitter(1000 * 6000 * 15L);
		sseEmitter.onCompletion(() -> {
			notificationService.removeEmitter(userId);
		});
		sseEmitter.onTimeout(() -> {
			sseEmitter.complete();
			notificationService.removeEmitter(userId);
		});
		notificationService.setUserEmitter(userId, sseEmitter);
		return sseEmitter;
	}
	@DeleteMapping("/delete")
	public void deleteById(){

	}
	@PostMapping("/find")
	public Set<Alarm> findAll(@RequestParam String name){
		return notificationService.findAllNoti(name);
	}
}