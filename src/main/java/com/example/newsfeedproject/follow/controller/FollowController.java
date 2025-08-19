package com.example.newsfeedproject.follow.controller;

import com.example.newsfeedproject.common.annoation.LoginUserResolver;
import com.example.newsfeedproject.follow.repository.FollowRepository;
import com.example.newsfeedproject.follow.service.FollowService;
import com.example.newsfeedproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class FollowController {
    private final FollowService followService;

    /**
     * 특정 사용자 팔로우
     */
    @PostMapping("/{userId}/follow")
    public ResponseEntity<String> followUser(
            @PathVariable Long userId,
            @LoginUserResolver User user
    ) {
        followService.followUser(userId, user);
        return ResponseEntity.ok().body("팔로우 되었습니다.");
    }
}
