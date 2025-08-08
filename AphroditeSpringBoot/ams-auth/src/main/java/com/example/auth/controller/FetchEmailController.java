package com.example.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.dto.UserIdDTO;
import com.example.auth.service.UserService;

@RestController
@RequestMapping("api/auth")
public class FetchEmailController {

    @Autowired
    private UserService userService;

    @PostMapping("/fetchemail")
    public ResponseEntity<String> fetchEmail(@RequestBody UserIdDTO request) {
        Long userId = request.getUserId();
        String email = userService.fetchUserEmail(userId);
        return ResponseEntity.ok(email);
    }
}
