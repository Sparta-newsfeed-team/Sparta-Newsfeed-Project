package com.example.newsfeedproject.domain.post.dto;

import com.example.newsfeedproject.domain.user.dto.AuthorResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostResponse(

        Long id,
        String title,
        String content,
        AuthorResponse author,
        Long likesCount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}
