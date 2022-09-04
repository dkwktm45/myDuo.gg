package com.project.MyDuo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.MyDuo.entity.Account;
import com.project.MyDuo.entity.Board;
import com.project.MyDuo.entity.BoardPositions;
import com.project.MyDuo.entity.convert.ReportListConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Data @Builder
public class BoardDto {

	private String boardUuid;
	private String boardName;
	private String boardContent;
	private int boardRecruitmentYn;

	@Schema(description = "생성 날짜", defaultValue = "2022-01-05")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate boardRegDt;

	private int boardMicYn;
	@Convert(converter = ReportListConverter.class)
	private List<BoardPositions> myPositions;
	@Convert(converter = ReportListConverter.class)
	private List<BoardPositions> otherPositions;

	@OneToMany(fetch = FetchType.LAZY )
	@JoinColumn(name = "board_id",updatable = false,insertable = false)
	private List<BoardParticipantsDto> boardParticipantsList;

	public BoardDto(Board board){
		this.boardContent = board.getBoardContent();
		this.boardUuid = board.getBoardUuid();
		this.boardName = board.getBoardName();
		this.boardRecruitmentYn = board.getBoardRecruitmentYn();
		this.boardRegDt = board.getBoardRegDt();
		this.boardMicYn = board.getBoardMicYn();
		this.myPositions = board.getMyPositions();
		this.otherPositions = board.getOtherPositions();
		this.accountDto = new AccountDto(board.getAccount());
		this.boardParticipantsList = new ArrayList(board.getBoardParticipantsList());
	}

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "user_id")
	private AccountDto accountDto;

}