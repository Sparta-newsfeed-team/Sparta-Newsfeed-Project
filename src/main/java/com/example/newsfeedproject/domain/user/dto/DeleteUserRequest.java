package com.example.newsfeedproject.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record DeleteUserRequest(

        @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
        String password
) {}
