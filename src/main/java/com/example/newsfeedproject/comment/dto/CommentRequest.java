package com.example.newsfeedproject.comment.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(

        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content
) {}
