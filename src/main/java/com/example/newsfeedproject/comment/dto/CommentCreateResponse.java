package com.example.newsfeedproject.comment.dto;

import java.time.LocalDateTime;

public record CommentCreateResponse(

        String content,
        Long userId,
        String username,
        LocalDateTime createdAt
) {}
