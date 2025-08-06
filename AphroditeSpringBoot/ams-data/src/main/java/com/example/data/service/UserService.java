package com.example.data.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.data.entity.User;
import com.example.data.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
