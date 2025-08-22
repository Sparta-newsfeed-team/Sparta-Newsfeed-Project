package com.example.newsfeedproject.common.dto;

import java.time.LocalDateTime;

public record GlobalApiResponse<T> (

        String status,
        String message,
        T data,
        LocalDateTime timestamp,
        int code
) { }
