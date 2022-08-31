package com.project.MyDuo.dao;

import com.project.MyDuo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
