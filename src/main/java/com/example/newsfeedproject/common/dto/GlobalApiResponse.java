package com.example.newsfeedproject.common.dto;

import com.example.newsfeedproject.common.exception.ErrorCode;

import java.time.LocalDateTime;

public record GlobalApiResponse<T> (

        String status,
        String message,
        T data,
        LocalDateTime timestamp,
        int code
) {
    public static <T> GlobalApiResponse<T> error(String message, ErrorCode errorCode) {
        return new GlobalApiResponse<>(
                errorCode.getCode(),
                message,
                null,
                LocalDateTime.now(),
                errorCode.getHttpStatus().value()
        );
    }
}
