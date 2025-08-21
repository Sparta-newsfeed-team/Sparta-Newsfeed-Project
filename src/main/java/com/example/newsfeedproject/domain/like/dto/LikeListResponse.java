package com.example.newsfeedproject.domain.like.dto;

import com.example.newsfeedproject.domain.user.dto.AuthorResponse;

import java.util.List;

public record LikeListResponse(

        List<AuthorResponse> authorList,
        Long totalLikes
) {}
