package com.example.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.dto.LikeRequestDTO;
import com.example.data.dto.LikeResponseDTO;
import com.example.data.dto.PostRequestDTO;
import com.example.data.dto.PostResponseDTO;
import com.example.data.service.LikeService;
import com.example.data.service.PostService;

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

    @PostMapping("/addpost")
    public PostResponseDTO PostData(@RequestBody PostRequestDTO postRequestDTO) {
        return postService.addPost(postRequestDTO);
    }

    @PostMapping("/like")
    public ResponseEntity<LikeResponseDTO> togglePostLike(
            @RequestBody LikeRequestDTO likeRequestDTO
    ) {
        Long currentUserId = likeRequestDTO.getUserId();
        Long currrntPostId = likeRequestDTO.getPostId();
        LikeResponseDTO response = likeService.togglePostLike(currentUserId, currrntPostId);
        return ok(response);
    }

}
