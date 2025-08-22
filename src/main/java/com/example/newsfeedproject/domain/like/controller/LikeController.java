package com.example.newsfeedproject.domain.like.controller;

import com.example.newsfeedproject.common.annotation.LoginUserResolver;
import com.example.newsfeedproject.common.dto.GlobalApiResponse;
import com.example.newsfeedproject.domain.like.dto.LikeResponse;
import com.example.newsfeedproject.domain.like.dto.LikeListResponse;
import com.example.newsfeedproject.domain.like.service.LikeService;
import com.example.newsfeedproject.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public GlobalApiResponse<LikeResponse> addLike(@LoginUserResolver User user, @PathVariable Long postId) {

        LikeResponse addedLikeResponse = likeService.addLike(user.getId(), postId);

        return GlobalApiResponse.ok("좋아요가 추가되었습니다.", addedLikeResponse);
    }

    @DeleteMapping
    public GlobalApiResponse<LikeResponse> deleteLike(@LoginUserResolver User user,@PathVariable Long postId) {

        LikeResponse deletedLike = likeService.deleteLike(user.getId(), postId);

        return GlobalApiResponse.ok("좋아요가 취소되었습니다.", deletedLike);
    }

    @GetMapping
    public GlobalApiResponse<LikeListResponse> getLike(@PathVariable Long postId) {

        LikeListResponse likeListResponse = likeService.getLikeList(postId);

        return GlobalApiResponse.ok("게시글 좋아요 목록이 조회되었습니다.", likeListResponse);
    }
}
