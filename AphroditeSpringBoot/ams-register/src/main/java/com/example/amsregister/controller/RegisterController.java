package com.example.amsregister.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // 状态
import org.springframework.web.bind.annotation.PostMapping; // 封装请求体用的
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.amsregister.dto.RegisterRequest;
import com.example.amsregister.dto.RegisterResponse;
import com.example.amsregister.model.Users;
import com.example.amsregister.repository.UserRepository;
import com.example.amsregister.service.PasswordService;
import com.example.amsregister.utils.TokenUtil;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {

    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final TokenUtil tokenUtil;

    @Autowired
    public RegisterController(UserRepository userRepository, PasswordService passwordService, TokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.tokenUtil = tokenUtil;
    }

    /**
     * 1. RegisterRequest 这个是我们封装好的类 要和 前端的发来的请求保持一致 2.
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> addUser(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>(new RegisterResponse(false, "用户名已被占用！"), HttpStatus.CONFLICT);
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>(new RegisterResponse(false, "该邮箱已被注册！"), HttpStatus.CONFLICT);
        }

        // 存入 数据库
        Users user = new Users();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setNickname(registerRequest.getUsername());
        String hashedPassword = passwordService.hashPassword(registerRequest.getPassword());
        user.setPasswordHash(hashedPassword);
        userRepository.save(user);

        // 生成token
        // 返回一个token 就可以
        final String token = tokenUtil.generateToken(user.getUsername());
        RegisterResponse response = new RegisterResponse(true, "用户注册成功！", token);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
