package com.project.MyDuo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.MyDuo.entity.BoardParticipants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardParticipantsDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long participantId; // 방번호
	private String userName; // 메시지 name
	private Long userId;
	private String roomId;
	private String boardName;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "board_id")
	private BoardDto boardDto;

	public BoardParticipantsDto(BoardParticipants participants){
		this.participantId = participants.getParticipantId();
		this.userName = participants.getUserName();
		this.userId = participants.getUserId();
		this.roomId = participants.getRoomId();
		this.boardName = participants.getBoard().getName();
		if(participants.getBoard() == null){
			this.boardDto = null;
		}else{
			this.boardDto = new BoardDto(participants.getBoard());
		}
	}

}
