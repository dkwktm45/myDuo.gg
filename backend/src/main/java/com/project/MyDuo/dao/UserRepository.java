package com.project.MyDuo.dao;

import com.project.MyDuo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Account,Long> {

    @Query("select u from Account u where u.email = :email and u.valid = 1")
    Optional<Account> findUserByEmail(@Param("email") String email);

    /*작성자: 정성수
    * 구현 이유 : Account가 valid한지 테스트 하기 위해 필요할 것 같아서.*/
    Account findByEmail(String email);
    Account findByBoardList_Uuid(String uuid);


    @Transactional
    @Modifying
    @Query("update Account u set u.valid = 0 where u.email = :email and u.valid = 1")
    void deleteUserByEmail(@Param("email") String email);

    Optional<Account> findById(Long aLong);

	  Account findByEmail(String name);
}
