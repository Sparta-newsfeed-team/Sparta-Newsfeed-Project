package com.example.newsfeedproject.domain.user.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

public record UpdateUserInfoRequest(

        @Size(max = 20, message = "유저명은 최대 20자까지 입력이 가능합니다.")
        String name,

        @Max(value = 120, message = "나이는 최대 120세까지 입력 가능합니다.")
        Integer age
) {

    @AssertTrue(message = "이름 또는 나이 중 하나는 반드시 입력해야 합니다.")
    public boolean isValidInput() {
        return name != null && age != null;
    }
}