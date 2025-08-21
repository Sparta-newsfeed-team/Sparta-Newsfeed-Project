package com.example.newsfeedproject.domain.like.controller;

import com.example.newsfeedproject.common.annotation.LoginUserResolver;
import com.example.newsfeedproject.domain.like.dto.LikeResponse;
import com.example.newsfeedproject.domain.like.dto.LikeListResponse;
import com.example.newsfeedproject.domain.like.service.LikeService;
import com.example.newsfeedproject.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeResponse> addLike(@LoginUserResolver User user, @Valid @PathVariable Long postId) {

        LikeResponse addedLikeResponse = likeService.addLike(postId, user.getId());

        return ResponseEntity.ok(addedLikeResponse);
    }

    @DeleteMapping
    public ResponseEntity<LikeResponse> deleteLike(@LoginUserResolver User user, @Valid @PathVariable Long postId) {

        LikeResponse deletedLike = likeService.deleteLike(postId, user.getId());

        return ResponseEntity.ok(deletedLike);
    }

    @GetMapping
    public ResponseEntity<LikeListResponse> getLike(@Valid @PathVariable Long postId) {

        LikeListResponse likeListResponse = likeService.getLikeList(postId);

        return ResponseEntity.ok(likeListResponse);
    }
}
