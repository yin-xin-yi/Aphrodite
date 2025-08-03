package com.example.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.dto.PostResponseDTO;
import com.example.data.service.PostSelfService;

/**
 * 获取个人的帖子 就用 get 请求 /data/selfpost/userid
 *
 * 知识点 补充 1. @RestController 时，就等于同时加上了 @Controller 和为类中所有方法都默认加上了
 *
 * @PathVariable 这可以申明 对应的 userId 在哪
 *
 * @ResponseBody 2.
 */
@RestController
@RequestMapping("/api/data/selfpost")
public class FetchSelfPosts {

    @Autowired
    private PostSelfService postService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> GetPosts(@PathVariable("userId") Long userId) {
        try {
            // 这边是获取帖子
            List<PostResponseDTO> allselfPostsDTO = postService.GetSelfPosts(userId);
            return ResponseEntity.ok(allselfPostsDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("获取帖子数据时发生错误: " + e.getMessage());
        }
    }

}
