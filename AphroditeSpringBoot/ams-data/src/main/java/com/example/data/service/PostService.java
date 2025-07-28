package com.example.data.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.data.dto.CountInfoDTO;
import com.example.data.dto.PostInfoDTO;
import com.example.data.dto.PostRequestDTO;
import com.example.data.dto.PostResponseDTO;
import com.example.data.dto.TimeInfoDTO;
import com.example.data.dto.UserInfoDTO;
import com.example.data.entity.Post;
import com.example.data.entity.User;
import com.example.data.exception.ResourceNotFind;
import com.example.data.repository.PostRepository;

import jakarta.transaction.Transactional;

@Service
public class PostService {

    // 如果没有 @Autowired
    // private PostRepository postRepository = new PostRepositoryImpl();
    // 就需要这么手动实现
    @Autowired
    private PostRepository postRepository;

    /**
     * 获取所有帖子，用于首页信息流
     */
    public List<PostResponseDTO> getAllPosts() {
        List<Post> postsWithUsers = postRepository.findAllWithUserOrderByCreatedAtDesc();
        return postsWithUsers.stream()
                .map(post -> convertToDTO(post, false)) // 调用下面的新方法
                .collect(Collectors.toList());
    }

    /**
     * 创建和增加一个帖子 Transactional 这个作用是创建 数据库的事务
     */
    @Transactional
    public PostResponseDTO addPost(PostRequestDTO post_out) {
        Post post = new Post();
        post.setAnonymous(post_out.getIsAnonymous());
        post.setContent(post_out.getContent());
        post.setTitle(post_out.getTitle());

        post.setStatus(post_out.getPostStatus());

        post.setBookmarkCount(0);
        post.setCommentCount(0);
        post.setLikeCount(0);
        post.setViewCount(0);

        User user = new User();
        user.setId(post_out.getUserId());
        post.setUser(user);
        Post savepost = postRepository.save(post);

        return buildResponse(savepost.getId(), post_out.getIsDeteal());
    }

    public PostResponseDTO buildResponse(Long postId, Boolean isdetail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFind("这个postid 不存在" + postId));
        return convertToDTO(post, isdetail);
    }

    /**
     *
     */
    private PostResponseDTO convertToDTO(Post post, boolean isdetail) {
        PostResponseDTO postResponseDTO = new PostResponseDTO();

        postResponseDTO.setAnonymous(post.isAnonymous());
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

        CountInfoDTO cid = new CountInfoDTO();
        cid.setCommentCount(post.getCommentCount());
        cid.setLikeCount(post.getLikeCount());
        cid.setViewCount(post.getViewCount());
        cid.setBookMarkCount(post.getBookmarkCount());

        TimeInfoDTO time = new TimeInfoDTO();

        if (post.getCreatedAt() != null) {
            time.setCreatedAt(post.getCreatedAt().toInstant());
        }
        if (post.getUpdatedAt() != null) {
            time.setUpdatedAt(post.getUpdatedAt().toInstant());
        }

        User user = post.getUser();

        if (user != null) {
            UserInfoDTO userinfo = new UserInfoDTO();
            userinfo.setUserId(user.getId());
            userinfo.setUsername(user.getUsername());
            postResponseDTO.setUser(userinfo);
        }

        postResponseDTO.setCount(cid);
        postResponseDTO.setPost(pid);
        postResponseDTO.setTime(time);
        postResponseDTO.setAnonymous(post.isAnonymous());

        return postResponseDTO;
    }
}
