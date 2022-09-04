package com.project.MyDuo.entity.redis;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom implements Serializable {
	private static final long serialVersionUID = 6494678977089006639L;

	private String roomId;

	@ElementCollection
	@CollectionTable(name = "userParticipant",joinColumns = @JoinColumn(name="room_id"))
	@Column(name="user_list")
	private Set<String> userList = new HashSet<>();

	public static ChatRoom create() {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.roomId = UUID.randomUUID().toString();
		return chatRoom;
	}
}