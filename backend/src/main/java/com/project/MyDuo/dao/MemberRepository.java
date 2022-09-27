package com.project.MyDuo.dao;

import com.project.MyDuo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    @Query("select m from Member m where m.email = :email and m.valid = 1")
    Member findMemberByEmail(@Param("email") String email);

    /*작성자: 정성수
    * 구현 이유 : Account가 valid한지 테스트 하기 위해 필요할 것 같아서.*/
    //Member findByEmail(String email);
    Member findByBoardList_Uuid(String uuid);

    @Transactional
    @Modifying
    @Query("update Member m set m.valid = 0 where m.email = :email and m.valid = 1")
    void deleteMemberByEmail(@Param("email") String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.name = :name where m.id = :id and m.valid = 1")
    void updateNameById(@Param(value="id")Long id, @Param(value="name")String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.password = :password where m.id = :id and m.valid = 1")
    void updatePasswordById(@Param(value="id")Long id, @Param(value="password")String password);

    //Optional<Member> findById(Long aLong);

	  //Member findByEmail(String name);
}
