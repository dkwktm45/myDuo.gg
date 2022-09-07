package com.project.MyDuo.service.LoLAccoutService;

import com.project.MyDuo.dao.LoLAccount.LoLAccountRepository;
import com.project.MyDuo.entity.LoLAccount.LoLAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service @RequiredArgsConstructor
public class LoLAccountService {
    private final LoLAccountRepository repository;

    public boolean existsByPuuid(String puuid) {return repository.existsByPuuid(puuid);}
    @Transactional
    public LoLAccount findByPuuid(String puuid) {return repository.findByPuuid(puuid);}

    public LoLAccount findByName(String summonerName) {return repository.findByName(summonerName);}

    public void deleteByPuuid(String puuid) {repository.deleteByPuuid(puuid);}
    public void restoreByPuuid(String puuid) {repository.restoreByPuuid(puuid);}
    public int countValidLoLAccount(String email) { return repository.countByUser_EmailAndValidTrue(email);}

    public LoLAccount save(LoLAccount entity) {return repository.save(entity);}

    public Map<String, String > getSimpleLoLAccountInfos(String email) {
        Map<String, String> map = new ConcurrentHashMap<>();
        List<LoLAccount> list = repository.findByValidTrueAndUser_Email(email);

        for (LoLAccount account : list)
            map.put(account.getName(), account.getPuuid());

        return map;
    }

}
