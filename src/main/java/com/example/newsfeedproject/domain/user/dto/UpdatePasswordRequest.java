package com.example.newsfeedproject.domain.user.dto;

import com.example.newsfeedproject.common.annotation.ValidPassword;
import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(

        @NotBlank(message = "현재 비밀번호는 필수 입력사항입니다.")
        String currentPassword,

        @NotBlank(message = "변경할 비밀번호를 입력해주세요.")
        @ValidPassword
        String newPassword
) {}
