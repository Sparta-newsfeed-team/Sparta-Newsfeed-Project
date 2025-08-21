package com.example.newsfeedproject.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePostContentRequest (

        @NotBlank(message = "내용은 필수 입력사항입니다.")
        @Size(max = 100, message = "내용은 최대 100자까지 입력이 가능합니다.")
        String content
) {}
