package com.example.data.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class TimeInfoDTO {

    private Instant createdAt;
    private Instant updatedAt;
}
