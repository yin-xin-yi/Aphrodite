package com.example.data.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 建议为详情查询添加事务注解

import com.example.data.dto.CountInfoDTO;
import com.example.data.dto.PostInfoDTO;
import com.example.data.dto.PostRequestDTO;
import com.example.data.dto.PostResponseDTO;
import com.example.data.dto.TimeInfoDTO;
import com.example.data.dto.UserInfoDTO;
import com.example.data.entity.Post;
import com.example.data.entity.User;
import com.example.data.exception.ResourceNotFind;
import com.example.data.repository.PostLikeRepository;
import com.example.data.repository.PostRepository;
import com.example.data.repository.UserRepository;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public PostResponseDTO getPostDetail(Long postId, Long currentUserId) {
        logger.info("Fetching detail for post ID: {} for user ID: {}", postId, currentUserId);
        postRepository.incrementViewCount(postId);
        return buildResponse(postId, true, currentUserId);
    }

    public List<PostResponseDTO> getAllPosts(Long userId) {
        logger.info("Fetching all posts for general feed.");
        List<Post> postsWithUsers = postRepository.findAllWithUserOrderByCreatedAtDesc();
        return postsWithUsers.stream()
                .map(post -> convertToDTO(post, false, userId))
                .collect(Collectors.toList());
    }

    public PostResponseDTO addPost(PostRequestDTO post_out) {
        Post post = new Post();
        logger.info("创建post的时候 传入的post_out 信息是{}", post_out);
        post.setAnonymous(post_out.getIsAnonymous());
        post.setContent(post_out.getContent());
        post.setTitle(post_out.getTitle());
        post.setStatus(post_out.getPostStatus());

        post.setBookmarkCount(0);
        post.setCommentCount(0);
        post.setLikeCount(0);
        post.setViewCount(0);

        if (post_out.getUserId() != null) {
            Optional<User> userOptional = userRepository.findById(post_out.getUserId());
            post.setUser(userOptional.orElse(null));
        } else {
            post.setUser(null);
        }
        Post savepost = postRepository.save(post);

        return buildResponse(savepost.getId(), false, post_out.getUserId());
    }

    public PostResponseDTO buildResponse(Long postId, Boolean isdetail, Long currentUserId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFind("这个postid 不存在: " + postId));

        return convertToDTO(post, Boolean.TRUE.equals(isdetail), currentUserId);
    }

    private PostResponseDTO convertToDTO(Post post, boolean isdetail, Long currentUserId) {
        PostResponseDTO postResponseDTO = new PostResponseDTO();

        PostInfoDTO pid = new PostInfoDTO();
        pid.setPostId(post.getId());
        pid.setTitle(post.getTitle());

        if (!isdetail) {
            String fullContent = post.getContent();
            final int SUMMARY_LENGTH = 150;
            if (fullContent != null && fullContent.length() > SUMMARY_LENGTH) {
                pid.setContent(fullContent.substring(0, SUMMARY_LENGTH) + "...");
            } else {
                pid.setContent(fullContent);
            }
        } else {
            pid.setContent(post.getContent());
        }
        postResponseDTO.setPost(pid);

        CountInfoDTO cid = new CountInfoDTO();
        cid.setCommentCount(post.getCommentCount());
        cid.setLikeCount(post.getLikeCount());
        cid.setViewCount(post.getViewCount());
        cid.setBookMarkCount(post.getBookmarkCount());
        postResponseDTO.setCount(cid);

        TimeInfoDTO time = new TimeInfoDTO();
        if (post.getCreatedAt() != null) {
            time.setCreatedAt(post.getCreatedAt().toInstant());
        }
        if (post.getUpdatedAt() != null) {
            time.setUpdatedAt(post.getUpdatedAt().toInstant());
        }
        postResponseDTO.setTime(time);

        User user = post.getUser();
        if (user != null) {
            UserInfoDTO userinfo = new UserInfoDTO();
            userinfo.setUserId(user.getId());
            userinfo.setUsername(user.getUsername());
            postResponseDTO.setUser(userinfo);
        } else {
            postResponseDTO.setUser(null);
        }

        postResponseDTO.setAnonymous(post.isAnonymous());
        postResponseDTO.setCode(200);

        if (currentUserId != null) {
            boolean isLiked = postLikeRepository.existsByUser_IdAndPost_Id(currentUserId, post.getId());
            postResponseDTO.setLiked(isLiked);
        } else {
            postResponseDTO.setLiked(false);
        }

        return postResponseDTO;
    }
}
