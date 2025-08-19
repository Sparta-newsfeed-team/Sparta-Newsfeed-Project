package com.example.newsfeedproject.follow.controller;

import com.example.newsfeedproject.common.annoation.LoginUserResolver;
import com.example.newsfeedproject.follow.dto.FollowingResponse;
import com.example.newsfeedproject.follow.repository.FollowRepository;
import com.example.newsfeedproject.follow.service.FollowService;
import com.example.newsfeedproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 특정 사용자 언팔로우
     */
    @DeleteMapping("/{userId}/follow")
    public ResponseEntity<String> unfollowUser(
            @PathVariable Long userId,
            @LoginUserResolver User user
    ) {
        followService.unfollowUser(userId, user);
        return ResponseEntity.ok().body("언팔로우 하였습니다.");
    }

    /**
     * 팔로잉 목록 조회
     */
    @GetMapping("/following")
    public ResponseEntity<List<FollowingResponse>> getFollowingUsers(
            @LoginUserResolver User user
    ) {
        List<FollowingResponse> followingList = followService.getFollowingList(user);
        return ResponseEntity.ok(followingList);
    }
}
