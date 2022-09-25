package com.project.MyDuo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter @Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "board_participants")
public class BoardParticipants {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long participantId;
	private String participantUuid;
	private String userName;
	private Long userId;
	private String roomId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "board_id")
	private Board board;

	public void toNoBoard(){
		this.board = null;
	}
}


