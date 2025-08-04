package com.example.data.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.data.dto.LikeResponseDTO;
import com.example.data.entity.Post;
import com.example.data.entity.PostLike;
import com.example.data.entity.User;
import com.example.data.exception.ResourceNotFind;
import com.example.data.repository.PostLikeRepository;
import com.example.data.repository.PostRepository;
import com.example.data.repository.UserRepository;

@Service
public class LikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeService(PostLikeRepository postLikeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public LikeResponseDTO togglePostLike(Long currentUserId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFind("Post not found with id: " + postId));
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFind("User not found with id: " + currentUserId));
        Optional<PostLike> likeOptional = postLikeRepository.findByUser_IdAndPost_Id(currentUserId, postId);
        boolean isLiked;

        if (likeOptional.isPresent()) {
            postLikeRepository.delete(likeOptional.get());
            post.setLikeCount(post.getLikeCount() - 1);
            isLiked = false;
        } else {
            PostLike newLike = new PostLike();
            newLike.setUser(user);
            newLike.setPost(post);

            postLikeRepository.save(newLike);
            post.setLikeCount(post.getLikeCount() + 1);
            isLiked = true;
        }

        postRepository.save(post);
        return new LikeResponseDTO(post.getLikeCount(), isLiked);
    }
}
