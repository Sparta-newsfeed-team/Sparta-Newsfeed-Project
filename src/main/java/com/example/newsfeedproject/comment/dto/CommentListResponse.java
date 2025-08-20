package com.example.newsfeedproject.comment.dto;

public record CommentListResponse(

        Long userId,
        String username,
        String content
) {}
