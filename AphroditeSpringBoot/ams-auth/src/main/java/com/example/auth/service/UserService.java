package com.example.auth.service;

import com.example.auth.model.Users;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String fetchUserEmail(Long userId) {
        Optional<Users> userOptional = userRepository.findById(userId);
        Users user = userOptional.orElseThrow(() -> new RuntimeException("用户不存在"));
        return user.getEmail();
    }
}
