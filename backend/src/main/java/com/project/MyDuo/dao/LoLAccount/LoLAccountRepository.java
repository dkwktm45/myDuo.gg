package com.project.MyDuo.dao.LoLAccount;

import com.project.MyDuo.entity.LoLAccount.LoLAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    List<LoLAccount> findByValidTrueAndUser_Email(String email);
}
