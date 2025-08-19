package com.example.newsfeedproject.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(

        @NotBlank(message = "유저명은 필수 입력사항입니다.")
        @Size(max = 20, message = "유저명은 최대 20자까지 입력이 가능합니다.")
        String name,

        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @Size(max = 3)
        int age,

        @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다.")
        String password
) {}
