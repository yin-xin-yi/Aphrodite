package com.example.data.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.dto.PostResponseDTO;
import com.example.data.service.PostService;

/**
 * 预备知识： 就是internalServerErrir 就是 内部服务器错误 标记为 500
 */
@RestController
@RequestMapping("api/data")
public class FetchDataController {

    @Autowired
    private PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(FetchDataController.class);

    @GetMapping("/fetchdata_onmounted/{userId}")
    public ResponseEntity<?> fetchData(@PathVariable("userId") Long userId) {
        try {
            logger.info("userId:{}", userId);
            List<PostResponseDTO> allPostsDTO = postService.getAllPosts(userId);
            return ResponseEntity.ok(allPostsDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("获取帖子数据时发生错误: " + e.getMessage());
        }
    }

    @GetMapping("/fetchpost/{postId}")
    public ResponseEntity<?> getPost(
            @PathVariable("postId") Long postId,
            @RequestParam(name = "userId", required = false) Long userId
    ) {
        try {
            PostResponseDTO onePostDTO = postService.getPostDetail(postId, userId);
            return ResponseEntity.ok(onePostDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("获取帖子数据时发生错误: " + e.getMessage());
        }

    }
}
