package com.example.newsfeedproject.domain.user.dto;

import com.example.newsfeedproject.common.annotation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(

        @NotBlank(message = "유저명은 필수 입력사항입니다.")
        @Size(max = 20, message = "유저명은 최대 20자까지 입력이 가능합니다.")
        String name,

        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @Max(value = 120, message = "나이는 최대 120세까지 입력 가능합니다.")
        Integer age,

        @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
        @ValidPassword
        String password
) {}
