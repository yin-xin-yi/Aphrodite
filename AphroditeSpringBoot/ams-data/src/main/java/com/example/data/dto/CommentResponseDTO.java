package com.example.data.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDTO {

    private Long commentId;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
    private UserInfoDTO author;
    // private List<CommentResponseDTO> replies; 
}
