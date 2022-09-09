package com.project.MyDuo.service.redis;

import com.project.MyDuo.entity.redis.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomRepository {

	// Redis
	private static final String CHAT_ROOMS = "CHAT_ROOM";
	public static final String USER_COUNT = "USER_COUNT"; // 채팅룸에 입장한 클라이언트수 저장
	private static final String FRIEND_ROOMS = "FRIEND_ROOMS";

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, ChatRoom> opsHashChatRoom;

	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valueOps;

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, ChatRoom> opsHashFriendRoom;

	// 모든 채팅방 조회
	public List<ChatRoom> findAllRoom(List<String> roomIdList) {
		return opsHashChatRoom.multiGet(CHAT_ROOMS, roomIdList);
	}

	// 특정 채팅방 조회
	public ChatRoom findRoomById(String id) {
		return opsHashChatRoom.get(CHAT_ROOMS, id);
	}

	public void updateChatRoom(ChatRoom chatRoom){
		opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
	}

	// 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
	public ChatRoom createChatRoom() {
		ChatRoom chatRoom = ChatRoom.create();
		try {
			opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
			return chatRoom;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chatRoom;
	}
	// 채팅방 유저수 조회
	public long getUserCount(String roomId) {
		return Long.valueOf(Optional.ofNullable(valueOps.get(USER_COUNT + "_" + roomId)).orElse("0"));
	}
	// 채팅방에 입장한 유저수 +1
	public long plusUserCount(String roomId) {
		return Optional.ofNullable(valueOps.increment(USER_COUNT + "_" + roomId)).orElse(0L);
	}
	// 채팅방에 입장한 유저수 -1
	public long minusUserCount(String roomId) {
		return Optional.ofNullable(valueOps.decrement(USER_COUNT + "_" + roomId)).filter(count -> count > 0).orElse(0L);
	}
	public void deleteRooms(List<String> roomIdList){
		List<String> chatRooms = opsHashChatRoom.multiGet(CHAT_ROOMS,roomIdList)
				.stream()
				.map(info -> info.getRoomId())
				.collect(Collectors.toList());
		opsHashChatRoom.delete(CHAT_ROOMS,chatRooms);
	}
	public void deleteRoom(String roomId){
		opsHashChatRoom.delete(CHAT_ROOMS,roomId);
	}

	public void save(ChatRoom chatRoom ) {
		opsHashFriendRoom.put(FRIEND_ROOMS, chatRoom.getRoomId(), chatRoom);
	}
}