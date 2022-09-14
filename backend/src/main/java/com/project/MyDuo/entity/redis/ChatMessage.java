package com.project.MyDuo.entity.redis;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage implements Serializable,Comparable<ChatMessage>  {
	private static final long serialVersionUID = 6494678977089006523L;



	@Override
	public int compareTo(ChatMessage chatMessage) {
		return getCreatedAt().compareTo(chatMessage.getCreatedAt());
	}

	public enum MessageType {
		ENTER, TALK , QUIT, DUO
	}
	// 메시지 타입 : 입장, 채팅
	private String messageId; // 방번호
	private MessageType type; // 메시지 타입
	private String roomId; // 방번호

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private LocalDateTime createdAt;
	private String sender; // 메시지 보낸사람
	private String message; // 메시지

	public static ChatMessage messageSetting(ChatMessage message) {
		if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
			message.setMessage("님이 방에 입장했습니다.");
			message.setSender(message.getSender());
		} else if (ChatMessage.MessageType.QUIT.equals(message.getType())) {
			message.setMessage("님이 방에서 나갔습니다.");
			message.setSender(message.getSender());
		}
		message.setCreatedAt(LocalDateTime.now());
		return message;
	}
}