package com.project.MyDuo.entity.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
public class Alarm  implements Serializable {
	private static final long serialVersionUID = 6494678977089006639L;

	@Nullable
	private String id;

	@NotNull
	private String senderId;

	@Nullable
	private String RoomId;

	private String senderName;

	@NotNull
	private Alarm.AlarmType alarmType;

	public enum AlarmType {
		FRIEND, DUO, AGREE , MESSAGE
	}
}
