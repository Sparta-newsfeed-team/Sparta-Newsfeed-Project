package com.example.newsfeedproject.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

public record UpdateUserInfoRequest(

        @Size(max = 20, message = "유저명은 최대 20자까지 입력이 가능합니다.")
        String name,

        @Max(value = 120, message = "나이는 최대 12세까지 입력 가능합니다.")
        Integer age
) {
    public boolean isEmpty() {
        return name == null && age == null;
    }
}