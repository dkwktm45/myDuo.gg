package com.project.MyDuo.service;

import com.project.MyDuo.dao.UserRepository;
import com.project.MyDuo.entity.Account;
import com.project.MyDuo.entity.redis.ChatRoom;
import com.project.MyDuo.security.CustomUser;
import com.project.MyDuo.service.redis.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRepositoryService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

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

    public void heartPlus(Authentication authentication, String roomId) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = ((CustomUser) userDetails).getAccount();
        account.setHeartPlus();
        String email = chatRoomRepository.findRoomById(roomId).getUserList()
            .stream().filter(info -> !info.equals(account.getEmail())).collect(Collectors.toList()).get(0);

        Account result = userRepository.findByEmail(email);
        result.setHeartPlus();
        userRepository.save(result);
    }
}
