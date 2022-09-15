package com.project.MyDuo.dao;

import com.project.MyDuo.dto.LoL.Info.LoLAccountInfoDto;
import com.project.MyDuo.dto.LoL.Info.LoLNameAndPuuidDto;
import com.project.MyDuo.entity.LoLAccount.LoLAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LoLAccountRepository extends JpaRepository<LoLAccount, Long> {
    boolean existsByPuuid(String puuid);
    LoLAccount findByPuuid(String puuid);
    LoLAccount findByName(String name);

    @Transactional
    @Modifying
    @Query("update LoLAccount l set l.valid = false where l.puuid = ?1")
    void deleteByPuuid(String puuid);

    @Transactional
    @Modifying
    @Query("update LoLAccount l set l.valid = true where l.puuid = ?1")
    void restoreByPuuid(String puuid);


    @Query("select count(l) from LoLAccount l where l.user.email = ?1 and l.valid = true")
    int countByUser_EmailAndValidTrue(String email);

    @Query(value = "SELECT " +
            "new com.project.MyDuo.dto.LoL.Info.LoLNameAndPuuidDto(l.name, l.puuid)" +
            "FROM LoLAccount l " +
            "INNER JOIN Member m " +
            "ON m.id = l.user.id AND m.email = :email AND l.valid = true")
    List<LoLNameAndPuuidDto> findValidLoLAccountInfos(@Param("email") String email);
}
