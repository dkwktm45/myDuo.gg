package com.project.MyDuo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.MyDuo.entity.Friend;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendDto {

	private String priendUuid;
	private String priendEmail;
	@Nullable
	private String priendRoomId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "user_id")
	private AccountDto accountDto;

	public FriendDto(Friend friend){
		this.priendUuid = friend.getPriendUuid();
		this.priendEmail = friend.getPriendEmail();
		this.priendRoomId = friend.getPriendRoomId();
		this.accountDto = new AccountDto(friend.getMember());
	}
}