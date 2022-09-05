package com.project.MyDuo.dao;

import com.project.MyDuo.entity.Account;
import com.project.MyDuo.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

	List<Friend> findByAccount(Account account);

	Friend findByAccountAndPriendUuid(Account account, String uuid);
}
