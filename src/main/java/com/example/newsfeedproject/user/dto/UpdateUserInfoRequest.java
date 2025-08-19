package com.example.newsfeedproject.user.dto;

import jakarta.validation.constraints.Size;

public record UpdateUserInfoRequest(

        @Size(max = 20, message = "유저명은 최대 20자까지 입력이 가능합니다.")
        String name,

        @Size(max = 3)
        int age
) {}
