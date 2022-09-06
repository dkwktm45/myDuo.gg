package com.project.MyDuo.service;

import com.project.MyDuo.dao.UserRepository;
import com.project.MyDuo.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRepositoryService {

    private final UserRepository userRepository;

    public void saveMember(Account account) {
        userRepository.save(account);
    }

    public Optional<Account> findById(Long id){return userRepository.findById(id);}

    public Optional<Account> findMember(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void deleteMember(String email) {
        userRepository.deleteUserByEmail(email);
    }
}
