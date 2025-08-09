package com.example.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentRequestDTO {

    private Long postId;
    private Long userId;
    private String content;
    private Long parentCommentId;
}
