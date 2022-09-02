package com.project.MyDuo.entity.redis;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom implements Serializable {
	private static final long serialVersionUID = 6494678977089006639L;

	public enum MessageType {
		FRIEND, DUO
	}

	private MessageType type;
	private String roomId;
	private String name;
	private Set<String> userId = new HashSet<>();
	private LocalDate createdAt;


	public static ChatRoom create(String name) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.roomId = UUID.randomUUID().toString();
		chatRoom.userId = new HashSet<>();
		chatRoom.name = name;
		chatRoom.createdAt = LocalDate.now();
		return chatRoom;
	}
}