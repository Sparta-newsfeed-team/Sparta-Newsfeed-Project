package com.example.newsfeedproject.domain.user.controller;

import com.example.newsfeedproject.common.annotation.LoginUserResolver;
import com.example.newsfeedproject.common.dto.GlobalApiResponse;
import com.example.newsfeedproject.domain.user.dto.UpdatePasswordRequest;
import com.example.newsfeedproject.domain.user.dto.UpdateUserInfoRequest;
import com.example.newsfeedproject.domain.user.dto.UserResponse;
import com.example.newsfeedproject.domain.user.entity.User;
import com.example.newsfeedproject.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 내 프로필 조회
    @GetMapping("/profile")
    public GlobalApiResponse<UserResponse> getUserInfo(@LoginUserResolver User user) {

        UserResponse profileResponse = userService.getUserInfo(user.getId());

        return GlobalApiResponse.ok("유저 정보가 조회되었습니다.", profileResponse);
    }

    // 유저 정보 조회
    @GetMapping("/{userId}")
    public GlobalApiResponse<UserResponse> getUserInfo(@PathVariable Long userId) {

        UserResponse userInfoResponse = userService.getUserInfo(userId);

        return GlobalApiResponse.ok("유저 정보가 조회되었습니다.", userInfoResponse);
    }

    // 유저 정보 수정
    @PatchMapping
    public GlobalApiResponse<UserResponse> updateUserInfo(@LoginUserResolver User user,
                                                          @RequestBody @Valid UpdateUserInfoRequest request) {

        UserResponse updateUserResponse = userService.updateUserInfo(user.getId(), request);

        return GlobalApiResponse.ok("유저 정보가 수정되었습니다.", updateUserResponse);
    }

    // 비밀번호 변경
    @PatchMapping("/password")
    public GlobalApiResponse<String> updatePassword(@LoginUserResolver User user,
                                                    @RequestBody @Valid UpdatePasswordRequest request) {

        userService.updatePassword(user.getId(), request);

        return GlobalApiResponse.ok("비밀번호가 변경되었습니다.", null);
    }
}
