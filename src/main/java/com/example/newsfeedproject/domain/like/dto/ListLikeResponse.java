package com.example.newsfeedproject.domain.like.dto;

import com.example.newsfeedproject.domain.user.dto.PostUserResponse;

import java.util.List;

public record ListLikeResponse(

        List<PostUserResponse> postUserResponseList,
        Long totalLikes
) {}
