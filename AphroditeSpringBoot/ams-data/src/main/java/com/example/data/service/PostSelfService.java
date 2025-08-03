package com.example.data.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.data.dto.CountInfoDTO;
import com.example.data.dto.DelPostResponseDTO;
import com.example.data.dto.PostInfoDTO;
import com.example.data.dto.PostResponseDTO;
import com.example.data.dto.TimeInfoDTO;
import com.example.data.dto.UserInfoDTO;
import com.example.data.entity.Post;
import com.example.data.entity.User;
import com.example.data.repository.PostRepository;

/**
 * 预备知识
 *
 * 告诉 Spring IoC 容器：“请扫描到我，为我创建一个实例 依赖注入的方式（通常使用 @Autowired 注解）来使用它，而不需要自己手动 new
 * PostSelfService() 这个 就比 C++ 掉了不知多少倍
 */
@Service
public class PostSelfService {

    @Autowired
    PostRepository postRepository;

    /**
     * 获取帖子
     */
    public List<PostResponseDTO> GetSelfPosts(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        List<PostResponseDTO> postDtos = posts.stream().map(post -> convertToDTO(post, false)) // 调用下面的新方法
                .collect(Collectors.toList());
        return postDtos;
    }

    /**
     * 删除帖子
     */
    public DelPostResponseDTO DelPost(Long postId) {
        DelPostResponseDTO delPostResponseDTO = new DelPostResponseDTO();
        try {
            postRepository.deleteById(postId);
            delPostResponseDTO.setCode(200);
            delPostResponseDTO.setMessage("帖子删除成功!");
        } catch (EmptyResultDataAccessException e) {
            delPostResponseDTO.setCode(500);
            delPostResponseDTO.setMessage("删除失败 服务器内部错误");
        }
        return delPostResponseDTO;
    }

    /**
     * 工具类 转换成 dto
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
        postResponseDTO.setCode(200);
        postResponseDTO.setCount(cid);
        postResponseDTO.setPost(pid);
        postResponseDTO.setTime(time);
        postResponseDTO.setAnonymous(post.isAnonymous());

        return postResponseDTO;
    }

}
