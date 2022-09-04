package com.project.MyDuo.entity.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
public class Notification implements Serializable {
	private static final long serialVersionUID = 6494678977089006639L;
	@NotNull
	private String receiveId;
	@ElementCollection
	@CollectionTable(name = "userParticipant",joinColumns = @JoinColumn(name="receiveId"))
	private Set<Alarm> alarmList = new HashSet<>();
}
