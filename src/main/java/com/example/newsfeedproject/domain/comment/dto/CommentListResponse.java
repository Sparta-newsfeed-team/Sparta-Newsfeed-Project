package com.example.newsfeedproject.domain.comment.dto;

public record CommentListResponse(

        Long userId,
        String username,
        String content
) {}
