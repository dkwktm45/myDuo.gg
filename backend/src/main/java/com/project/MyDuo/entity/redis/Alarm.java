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

	@NotNull
	private String senderId;

	private String senderName;
	@Nullable
	private String RoomId;
	@NotNull
	private Alarm.AlarmType alarmType;

	public enum AlarmType {
		FRIEND, DUO, AGREE
	}
}
