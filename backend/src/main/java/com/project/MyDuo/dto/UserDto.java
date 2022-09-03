package com.project.MyDuo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
public class UserDto {

	private Long userId;
	private String userName;
	private String userEmail;
	private String userPwd;
	private int userHeart;

	@OneToMany(fetch = FetchType.LAZY )
	@JoinColumn(name = "user_id",updatable = false,insertable = false)
	private List<BoardDto> boardDtoList;
}

