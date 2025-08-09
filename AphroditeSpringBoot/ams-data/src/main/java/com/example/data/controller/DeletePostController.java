package com.example.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.dto.DelPostRequestDTO;
import com.example.data.dto.DelPostResponseDTO;
import com.example.data.service.PostSelfService;

@RestController
@RequestMapping("api/data")
public class DeletePost {

    @Autowired
    PostSelfService postSelfService;

    @PostMapping("/delpost")
    public ResponseEntity<?> DelPost(@RequestBody DelPostRequestDTO delPostRequestDTO) {
        Long postId = delPostRequestDTO.getPostId();
        DelPostResponseDTO responseDTO = postSelfService.DelPost(postId);
        return ResponseEntity.ok(responseDTO);
    }

}
