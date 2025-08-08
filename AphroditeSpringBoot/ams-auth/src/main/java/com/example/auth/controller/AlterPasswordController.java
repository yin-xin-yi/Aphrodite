package com.example.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.dto.AlterPdDTO;
import com.example.auth.service.AlterPassword;

@RestController
@RequestMapping("api/auth")
public class AlterPasswordController {

    @Autowired
    private AlterPassword alterPasswordService;

    @PostMapping("/alterpd")
    public ResponseEntity<String> alterPassword(@RequestBody AlterPdDTO request) {
        alterPasswordService.alterUserPassword(request);
        return ResponseEntity.ok("密码修改成功");
    }
}
