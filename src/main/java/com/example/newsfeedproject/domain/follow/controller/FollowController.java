package com.example.newsfeedproject.domain.follow.controller;

import com.example.newsfeedproject.common.annotation.LoginUserResolver;
import com.example.newsfeedproject.common.dto.GlobalApiResponse;
import com.example.newsfeedproject.domain.follow.dto.FollowerResponse;
import com.example.newsfeedproject.domain.follow.dto.FollowingResponse;
import com.example.newsfeedproject.domain.follow.service.FollowService;
import com.example.newsfeedproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class FollowController {

    private final FollowService followService;

    // 특정 사용자 팔로우
    @PostMapping("/{userId}/follow")
    public GlobalApiResponse<?> followUser(@PathVariable Long userId,
                                           @LoginUserResolver User user) {

        followService.followUser(userId, user);

        return GlobalApiResponse.ok("팔로우 되었습니다.", null);
    }

    // 특정 사용자 언팔로우
    @DeleteMapping("/{userId}/follow")
    public GlobalApiResponse<?> unfollowUser(@PathVariable Long userId,
                                             @LoginUserResolver User user) {

        followService.unfollowUser(userId, user);

        return GlobalApiResponse.ok("언팔로우 하였습니다.", null);
    }

    // 팔로잉 목록 조회
    @GetMapping("/following")
    public GlobalApiResponse<List<FollowingResponse>> getFollowingList(@LoginUserResolver User user) {

        List<FollowingResponse> followingList = followService.getFollowingList(user);

        return GlobalApiResponse.ok("팔로잉 목록이 조회되었습니다.", followingList);
    }

    // 팔로워 목록 조회
    @GetMapping("/followers")
    public GlobalApiResponse<List<FollowerResponse>> getFollowerList(@LoginUserResolver User user) {

        List<FollowerResponse> followerList = followService.getFollowerList(user);

        return GlobalApiResponse.ok("팔로워 목록이 조회되었습니다.", followerList);
    }
}
