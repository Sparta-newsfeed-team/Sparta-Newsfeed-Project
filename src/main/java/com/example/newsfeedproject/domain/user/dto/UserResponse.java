package com.example.newsfeedproject.domain.user.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}
