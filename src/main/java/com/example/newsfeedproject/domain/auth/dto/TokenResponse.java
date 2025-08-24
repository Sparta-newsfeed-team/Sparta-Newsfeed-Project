package com.example.newsfeedproject.domain.auth.dto;

public record TokenResponse(

        String accessToken,
        String refreshToken
) {}
