package com.project.MyDuo.service;

import com.project.MyDuo.dao.UserRepository;
import com.project.MyDuo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRepositoryService {

    private final UserRepository userRepository;

    public void saveMember(User user) {
        userRepository.save(user);
    }

    public Optional<User> findMember(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void deleteMember(String email) {
        userRepository.deleteUserByEmail(email);
    }
}
