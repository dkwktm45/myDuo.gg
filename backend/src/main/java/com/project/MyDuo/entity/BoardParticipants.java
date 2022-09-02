package com.project.MyDuo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Builder
@AllArgsConstructor
@Entity @Setter
@NoArgsConstructor
@Table(name = "board_participants")
public class BoardParticipants {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long participantId; // 방번호
	private String userName; // 메시지 name
	private Long userId;
	private String roomId;


	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "board_id")
	private Board board;
}


