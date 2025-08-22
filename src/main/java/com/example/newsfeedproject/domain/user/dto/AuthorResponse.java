package com.example.newsfeedproject.domain.user.dto;

import com.example.newsfeedproject.domain.user.entity.User;

public record AuthorResponse(

        Long id,
        String name
) {
    public AuthorResponse(User user) {
        this(user.getId(), user.getName());
    }
}
