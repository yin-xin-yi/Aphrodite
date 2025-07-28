package com.example.data.dto;

import lombok.Data;

@Data
public class UserInfoDTO {

    private Long userId;
    private String username;

    public String getUsername() {
        return username;
    }

    public Long getUserid() {
        return userId;
    }
}
