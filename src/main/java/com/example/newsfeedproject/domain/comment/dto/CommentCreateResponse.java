package com.example.newsfeedproject.domain.comment.dto;

import java.time.LocalDateTime;

public record CommentCreateResponse(

        String content,
        Long userId,
        String username,
        LocalDateTime createdAt
) {}
