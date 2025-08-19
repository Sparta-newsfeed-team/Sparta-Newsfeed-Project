package com.example.newsfeedproject.user.dto;

import com.example.newsfeedproject.user.entity.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long id, String name, String email,
        LocalDateTime createdAt, LocalDateTime modifiedAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(), user.getName(), user.getEmail(),
                user.getCreatedAt(), user.getModifiedAt());
    }
}
