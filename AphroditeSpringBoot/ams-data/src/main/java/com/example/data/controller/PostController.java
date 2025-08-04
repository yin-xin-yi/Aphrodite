package com.example.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.dto.LikeResponseDTO;
import com.example.data.dto.PostRequestDTO;
import com.example.data.dto.PostResponseDTO;
import com.example.data.entity.User;
import com.example.data.service.LikeService;
import com.example.data.service.PostService;

// @RestController = @Controller + @ResponseBody
// 类中的方法可以使用@RequestMapping, @GetMapping, @PostMapping
// 希望直接返回数据 就是 要加上这个 @ResponseBody
@RestController
@RequestMapping("api/data")
public class PostController {

    private PostService postService;
    private LikeService likeService;

    @Autowired
    public PostController(PostService postService, LikeService likeService) {
        this.postService = postService;
        this.likeService = likeService;
    }

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/addpost")
    public PostResponseDTO PostData(@RequestBody PostRequestDTO postRequestDTO) {
        return postService.addPost(postRequestDTO);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<LikeResponseDTO> togglePostLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal User currentUser
    ) {
        Long currentUserId = currentUser.getId();
        LikeResponseDTO response = likeService.togglePostLike(currentUserId, postId);
        return ok(response);
    }

}
