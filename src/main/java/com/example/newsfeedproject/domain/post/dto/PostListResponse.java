package com.example.newsfeedproject.domain.post.dto;

import java.util.List;

public record PostListResponse(

        List<PostResponse> posts,
        int page,
        int totalPages,
        long totalElements
) {
}
