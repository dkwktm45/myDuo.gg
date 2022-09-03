package com.project.MyDuo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.MyDuo.dto.BoardDto;
import com.project.MyDuo.entity.convert.ReportListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardId;
	private String boardName;
	private String boardContent;
	private int boardRecruitmentYn;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate boardRegDt;
	private int boardMicYn;
	@Convert(converter = ReportListConverter.class)
	private List<BoardPositions> myPositions;
	@Convert(converter = ReportListConverter.class)
	private List<BoardPositions> otherPositions;

	public Board(BoardDto boardDto) {
		this.boardContent = boardDto.getBoardContent();
		this.boardName = boardDto.getBoardName();
		this.boardRecruitmentYn = boardDto.getBoardRecruitmentYn();
		this.boardRegDt = boardDto.getBoardRegDt();
		this.boardMicYn = boardDto.getBoardMicYn();
		this.myPositions = boardDto.getMyPositions();
		this.otherPositions = boardDto.getOtherPositions();
//		this.member = new Member(boardDto.getUserDto());
	}

	@OneToMany(fetch = FetchType.EAGER )
	@JoinColumn(name = "board_id",updatable = false,insertable = false)
	private List<BoardParticipants> boardParticipantsList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "user_id")
	private Account account;
}
