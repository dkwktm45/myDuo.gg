package com.project.MyDuo.service;

import com.project.MyDuo.dao.LoLAccountRepository;
import com.project.MyDuo.dto.LoL.Info.LoLAccountInfoDto;
import com.project.MyDuo.dto.LoL.Info.LoLNameAndPuuidDto;
import com.project.MyDuo.entity.LoLAccount.LoLAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class LoLAccountService {
    private final LoLAccountRepository repository;

    public boolean existsByPuuid(String puuid) {return repository.existsByPuuid(puuid);}
    public LoLAccount findByPuuid(String puuid) {return repository.findByPuuid(puuid);}

    public LoLAccount findByName(String summonerName) {return repository.findByName(summonerName);}

    public void deleteByPuuid(String puuid) {repository.deleteByPuuid(puuid);}
    public void restoreByPuuid(String puuid) {repository.restoreByPuuid(puuid);}
    public int countValidLoLAccount(String email) { return repository.countByUser_EmailAndValidTrue(email);}

    public LoLAccount save(LoLAccount entity) {return repository.save(entity);}

    public List<LoLNameAndPuuidDto> getSimpleLoLAccountInfos(String email) {
        return repository.findValidLoLAccountInfos(email);
    }
}
