package com.project.MyDuo.service;

import com.project.MyDuo.entity.redis.Alarm;
import com.project.MyDuo.entity.redis.Notification;
import com.project.MyDuo.service.redis.NotifyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationService {
	private final Map<String, SseEmitter> connectedUser = new ConcurrentHashMap<>();
	private final NotifyRepository notifyRepository;
	public void setUserEmitter(String userId, SseEmitter sseEmitter) {
		log.info("알림 유저 들러옴" + userId);
		if(userId == null){
			throw new RuntimeException("유저 아이디가 null 입니다.");
		}
		connectedUser.put(userId, sseEmitter);
	}
	/** 보내는 메시지 유형
	 * 채팅방에 대한 알림
	 * 친구 요청에 대한 수락 버튼
	 * 그렇다면 notify 에대한 테이블은 roomId 있을 필요는 없다.?
	 **/
	public void sendMessage(String receiveId, Map<String, Object> result) throws IOException {
		Notification notification = (Notification) result.get("notification");
		Alarm noti = (Alarm) result.get("noti");

		if(connectedUser.containsKey(receiveId)) {
			connectedUser.get(receiveId).send(noti, MediaType.APPLICATION_JSON);
			notifyRepository.saveNoti(notification);
		}else{
			notifyRepository.saveNoti(notification);
		}
	}
	public Set<Alarm> findAllNoti(String name){
		Optional<Notification> notification = notifyRepository.findByReceive(name);
		if(notification.isEmpty()){
			return null;
		}else{
			return notification.get().getAlarmList();
		}
	}

	public void chatType(String receiveId, String sender , String roomId , Alarm.AlarmType type) throws IOException {
		Set<Alarm> alarms = null;
		Optional<Notification> notificationOptional = notifyRepository.findByReceive(receiveId);
		Notification notification =null;
		Alarm noti = Alarm.builder().alarmType(type).senderId(sender).senderName("name").id(roomId).build();

		if(notificationOptional.isEmpty()){
			alarms = new HashSet<>();
			alarms.add(noti);
			notification = Notification.builder().receiveId(receiveId).alarmList(alarms).build();
		}else{
			alarms = notificationOptional.get().getAlarmList();
			alarms.add(noti);
			notificationOptional.get().setAlarmList(alarms);
			notification = notificationOptional.get();
		}
		Map<String, Object> result = new HashMap<>();
		result.put("notification",notification);
		result.put("noti",noti);

		sendMessage(receiveId, result);
	}

	public void agreeTyp(String senderEmail, String receiveEmail, Alarm.AlarmType type){
		Set<Alarm> alarms = null;
		Notification notification =null;

		Optional<Notification> notificationOptional = notifyRepository.findByReceive(receiveEmail);

		Alarm noti = Alarm.builder().alarmType(type).id(UUID.randomUUID().toString())
				.senderId(senderEmail).senderName(senderEmail).build();

		if(notificationOptional.isEmpty()){
			alarms = new HashSet<>();
			alarms.add(noti);
			notification = Notification.builder().receiveId(receiveEmail).alarmList(alarms).build();
		}else{
			alarms = notificationOptional.get().getAlarmList();
			alarms.add(noti);
			notificationOptional.get().setAlarmList(alarms);
			notification = notificationOptional.get();
		}
		Map<String, Object> result = new HashMap<>();
		result.put("notification",notification);
		result.put("noti",noti);

		try{
			sendMessage(receiveEmail, result);
		}catch (Exception e){
			throw new RuntimeException();
		}
	};

	public void deleteAlarm(String roomId,String userName){
		notifyRepository.deleteAlarm(roomId,userName);
	}
	public void removeEmitter(String userId) {
		connectedUser.remove(userId);
	}
}
