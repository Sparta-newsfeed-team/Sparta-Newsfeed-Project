package com.example.newsfeedproject.common.exception;

import com.example.newsfeedproject.common.dto.GlobalApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalApiResponse> ValidationExceptionHandler(MethodArgumentNotValidException e) {

        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        GlobalApiResponse response = new GlobalApiResponse(errorCode.getCode(), errorMessage);

        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }
}