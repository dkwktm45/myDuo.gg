package com.project.MyDuo.dao;

import com.project.MyDuo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
