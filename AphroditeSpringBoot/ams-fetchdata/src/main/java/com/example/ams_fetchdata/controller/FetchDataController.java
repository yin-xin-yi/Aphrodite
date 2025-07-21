package com.example.ams_fetchdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ams_fetchdata.entity.Post;
import com.example.ams_fetchdata.service.PostService;

@RestController
@RequestMapping("api/data")
public class FetchDataController {

    @Autowired
    private PostService postService;

    @GetMapping("/fetchdata_onmounted")
    public ResponseEntity<?> fetchData() {
        try {
            List<Post> allPosts = postService.getAllPosts();
            return ResponseEntity.ok(allPosts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("获取帖子数据时发生错误: " + e.getMessage());
        }
    }
}
