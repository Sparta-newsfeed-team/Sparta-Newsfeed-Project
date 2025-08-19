package com.example.newsfeedproject.post.dto;

import com.example.newsfeedproject.user.dto.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<UserResponse> users
) {}
