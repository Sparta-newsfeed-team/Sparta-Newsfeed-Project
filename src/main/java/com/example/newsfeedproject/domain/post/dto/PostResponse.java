package com.example.newsfeedproject.domain.post.dto;

import com.example.newsfeedproject.domain.user.dto.AuthorResponse;

import java.time.LocalDateTime;

public record PostResponse(

        Long id,
        String title,
        String content,
        AuthorResponse author,
        Long likesCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
