package com.example.newsfeedproject.like.controller;

import com.example.newsfeedproject.common.annoation.LoginUserResolver;
import com.example.newsfeedproject.like.dto.LikeResponse;
import com.example.newsfeedproject.like.dto.ListLikeResponse;
import com.example.newsfeedproject.like.service.LikeService;
import com.example.newsfeedproject.user.entity.User;
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
    public ResponseEntity<LikeResponse> addlike(@LoginUserResolver User user, @Valid @PathVariable Long postId) {

        LikeResponse likeResponse = likeService.addlike(postId, user.getId());

        return ResponseEntity.ok(likeResponse);
    }

    @DeleteMapping
    public ResponseEntity<LikeResponse> deletelike(@LoginUserResolver User user, @Valid @PathVariable Long postId) {

        LikeResponse deletelike = likeService.deletelike(postId, user.getId());

        return ResponseEntity.ok(deletelike);
    }

    @GetMapping
    public ResponseEntity<ListLikeResponse> getlike(@Valid @PathVariable Long postId) {

        ListLikeResponse listLikeResponse = likeService.getLikeList(postId);

        return ResponseEntity.ok(listLikeResponse);
    }
}
