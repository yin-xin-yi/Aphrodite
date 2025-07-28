package com.example.data.dto;

import lombok.Data;

@Data
public class PostResponseDTO {

    /**
     * 1. 发送回复的请求体 (1) 信息 内容 就是渲染到前端的 (2) code 就是判断一下 (3) postid 就是数据库 返回的 (4)
     * 更新时间 view_count 默认是 0 like_count 默认也是 0
     *
     */
    private boolean isAnonymous;

    private UserInfoDTO user;

    private PostInfoDTO post;

    private TimeInfoDTO time;

    private CountInfoDTO count;

}
