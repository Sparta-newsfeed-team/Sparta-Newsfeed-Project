package com.example.newsfeedproject.domain.post.dto;

import com.example.newsfeedproject.domain.user.dto.UserResponse;

import java.time.LocalDateTime;

public record PostResponse(

        Long id,
        String title,
        String content,
        Long likesCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UserResponse userResponse
) {}
