package com.example.newsfeedproject.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        Long id,
        String name,
        String email,
        Integer age,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {}
