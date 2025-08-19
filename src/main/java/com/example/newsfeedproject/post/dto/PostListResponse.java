package com.example.newsfeedproject.post.dto;

import java.util.List;

public record PostListResponse(
        List<PostResponse> posts,
        int page,
        int totalPages,
        long totalElements
) {
}
