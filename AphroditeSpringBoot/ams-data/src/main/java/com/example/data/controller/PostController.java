package com.example.data.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.dto.PostRequestDTO;
import com.example.data.dto.PostResponseDTO;
import com.example.data.service.PostService;

// @RestController = @Controller + @ResponseBody
// 类中的方法可以使用@RequestMapping, @GetMapping, @PostMapping
// 希望直接返回数据 就是 要加上这个 @ResponseBody
@RestController
@RequestMapping("api/data")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/addpost")
    public PostResponseDTO PostData(@RequestBody PostRequestDTO post) {
        return postService.addPost(post);
    }
}
