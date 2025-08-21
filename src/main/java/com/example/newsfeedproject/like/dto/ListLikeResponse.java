package com.example.newsfeedproject.like.dto;

import com.example.newsfeedproject.user.dto.PostUserResponse;

import java.util.List;

public record ListLikeResponse(

        List<PostUserResponse> postUserResponseList,
        Long totalLikes
) {}
