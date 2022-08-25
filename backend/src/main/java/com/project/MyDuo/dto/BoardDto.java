package com.project.MyDuo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.MyDuo.entity.BoardPositions;
import com.project.MyDuo.entity.convert.ReportListConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BoardDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardId;
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

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "user_id")
	private UserDto userDto;

}