package com.project.MyDuo.dto;

import com.project.MyDuo.entity.Account;
import com.project.MyDuo.entity.Board;
import com.project.MyDuo.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@NotNull
	@Embedded
	@Column(name = "user_email", length = 100)
	private String email;

	@NotNull
	@Column(name = "user_name", length = 100)
	private String name;

	@NotNull
	@Column(name = "user_pwd", length = 200)
	private String password;

	@NotNull
	@Column(name = "user_heart")
	private int heart;

	@NotNull
	@Column(name = "user_valid")
	private Boolean valid;

	private Role role;

	@OneToMany(fetch = FetchType.LAZY )
	@JoinColumn(name = "user_id",updatable = false,insertable = false)
	private List<BoardDto> boardDtoList;

	public AccountDto(Account account){
		this.id = account.getId();
		this.name = account.getName();
		this.email = account.getEmail();
		this.heart = account.getHeart();
		this.password = account.getPassword();
	}
}

