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

    @Transactional
    @Modifying

    @Query("update Account u set u.valid = 0 where u.email = :email and u.valid = 1")
    void deleteUserByEmail(@Param("email") String email);

    Optional<Account> findById(Long aLong);
}
