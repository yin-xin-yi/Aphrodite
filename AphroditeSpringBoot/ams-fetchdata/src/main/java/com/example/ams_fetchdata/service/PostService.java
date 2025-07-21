package com.example.ams_fetchdata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ams_fetchdata.entity.Post;
import com.example.ams_fetchdata.repository.PostRepository;

@Service
public class PostService {

    // 如果没有 @Autowired
    // private PostRepository postRepository = new PostRepositoryImpl();
    // 就需要这么手动实现
    @Autowired
    private PostRepository postRepository;

    /**
     * 获取所有帖子，用于首页信息流
     *
     */
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }
}
