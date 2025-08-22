package com.example.newsfeedproject.common.dto;

import com.example.newsfeedproject.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record GlobalApiResponse<T> (

        String status,
        String message,
        T data,
        LocalDateTime timestamp,
        int code
) {
    /**
     * 오류 메시지를 반환
     */
    public static <T> GlobalApiResponse<T> error(String message, ErrorCode errorCode) {
        return new GlobalApiResponse<>(
                errorCode.getCode(),
                message,
                null,
                LocalDateTime.now(),
                errorCode.getHttpStatus().value()
        );
    }

    /**
     * 성공 메시지를 반환
     * @param httpStatus HttpStatus
     * @param message Success Message
     * @param data Response Data
     */
    public static <T> GlobalApiResponse<T> success(HttpStatus httpStatus, String message, T data) {
        return new GlobalApiResponse<>(
                httpStatus.getReasonPhrase(),
                message,
                data,
                LocalDateTime.now(),
                httpStatus.value()
        );
    }

    public static <T> GlobalApiResponse<T> ok(String message, T data) {
        return GlobalApiResponse.success(HttpStatus.OK, message, data);
    }


}
