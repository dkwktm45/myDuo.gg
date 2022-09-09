package com.project.MyDuo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.MyDuo.entity.Board;
import com.project.MyDuo.entity.LoLAccount.LaneType;
import com.project.MyDuo.entity.convert.ReportListConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
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
	private Boolean boardRecruitmentYn, boardMicYn;

	@Schema(description = "생성 날짜", defaultValue = "2022-01-05")
	private Timestamp boardRegDt;

	@Convert(converter = ReportListConverter.class)
	private List<LaneType> myPositions;
	@Convert(converter = ReportListConverter.class)
	private List<LaneType> otherPositions;

	@OneToMany(fetch = FetchType.LAZY )
	@JoinColumn(name = "board_id",updatable = false,insertable = false)
	private List<BoardParticipantsDto> boardParticipantsList;

	public BoardDto(Board board){
		this.boardContent = board.getContent();
		this.boardUuid = board.getUuid();
		this.boardName = board.getName();
		this.boardRecruitmentYn = board.getClosingStatus();
		this.boardRegDt = board.getRegistrationTime();
		this.boardMicYn = board.getMicEnabled();
		this.myPositions = board.getMyPositions();
		this.otherPositions = board.getOtherPositions();
		this.accountDto = new AccountDto(board.getMember());
		this.boardParticipantsList = new ArrayList(board.getBoardParticipantsList());
	}

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "user_id")
	private AccountDto accountDto;

}