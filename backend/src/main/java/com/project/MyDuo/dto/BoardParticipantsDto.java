package com.project.MyDuo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class BoardParticipantsDto {
	private Long participantId; // 방번호
	private String userName; // 메시지 name

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "board_id")
	private BoardDto board;
}
