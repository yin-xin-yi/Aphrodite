package com.example.data.dto;

import com.example.data.entity.PostStatus;

import lombok.Data;

@Data
public class PostRequestDTO {

    private Long userId;
    private String title;
    private String content;
    private Boolean isAnonymous;
    private Boolean isDeteal;
    private PostStatus postStatus;
}
