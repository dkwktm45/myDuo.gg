package com.project.MyDuo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.MyDuo.entity.LoLAccount.LaneType;
import com.project.MyDuo.entity.convert.ReportListConverter;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "board")
public class Board {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long id;

	@Column(name = "board_uuid", unique = true)
	private String uuid;

	@Column(name = "lol_puuid")
	private String lolPuuid;

	@Column(name = "board_name")
	private String name;

	@Column(name = "board_content", length = 100)
	private String content;

	@Column(name = "closing_status")
	private Boolean closingStatus;

	@Column(name = "mic_enabled")
	private Boolean micEnabled;

	@Column(name = "registration_time")
	private Timestamp registrationTime;

	@Convert(converter = ReportListConverter.class)
	@Column(name = "my_positions")
	private List<LaneType> myPositions;

	@Column(name = "other_positions")
	@Convert(converter = ReportListConverter.class)
	private List<LaneType> otherPositions;

	@OneToMany(fetch = FetchType.EAGER )
	@JoinColumn(name = "board_id",updatable = false,insertable = false)
	private List<BoardParticipants> boardParticipantsList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "user_id")
	private Member member;

	public void changeStatus(Boolean flag) {
		this.closingStatus = flag;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Board(String uuid, String lolPuuid, String name, String content, Boolean closingStatus, Boolean micEnabled, Timestamp registrationTime, List<LaneType> myPositions, List<LaneType> otherPositions, Member member) {
		this.uuid = uuid;
		this.lolPuuid = lolPuuid;
		this.name = name;
		this.content = content;
		this.closingStatus = closingStatus;
		this.micEnabled = micEnabled;
		this.registrationTime = registrationTime;
		this.myPositions = myPositions;
		this.otherPositions = otherPositions;
		this.member = member;
	}
}
