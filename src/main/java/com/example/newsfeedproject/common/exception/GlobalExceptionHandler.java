package com.example.newsfeedproject.common.exception;

import com.example.newsfeedproject.common.dto.GlobalApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<GlobalApiResponse> businessExceptionHandler(BusinessException e) {

        ErrorCode errorCode = e.getErrorCode();
        GlobalApiResponse response = new GlobalApiResponse(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }
}