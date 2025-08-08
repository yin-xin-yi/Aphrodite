package com.example.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.auth.dto.AlterPdDTO;

import com.example.auth.model.Users;
import com.example.auth.repository.UserRepository;

@Service
public class AlterPassword {

    private static final Logger logger = LoggerFactory.getLogger(AlterPassword.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    public void alterUserPassword(AlterPdDTO request) {
        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    logger.warn("尝试修改密码但用户不存在,ID: {}", request.getUserId());
                    return new RuntimeException("用户不存在");
                });

        if (!passwordService.checkPassword(request.getCurrentPassword(), user.getPasswordHash())) {
            logger.warn("用户 {} 尝试修改密码，但当前密码不正确", request.getUserId());
            throw new IllegalArgumentException("当前密码不正确");
        }

        if (passwordService.checkPassword(request.getNewPassword(), user.getPasswordHash())) {
            logger.warn("用户 {} 尝试修改密码，但新密码与当前密码相同", request.getUserId());
            throw new IllegalArgumentException("新密码不能与当前密码相同");
        }

        String newHashedPassword = passwordService.hashPassword(request.getNewPassword());
        user.setPasswordHash(newHashedPassword);
        userRepository.save(user);

        logger.info("用户 {} 密码修改成功", request.getUserId());
    }
}
