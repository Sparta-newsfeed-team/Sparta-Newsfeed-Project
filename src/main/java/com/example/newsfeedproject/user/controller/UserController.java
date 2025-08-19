package com.example.newsfeedproject.user.controller;

import com.example.newsfeedproject.common.annoation.LoginUserResolver;
import com.example.newsfeedproject.user.dto.UpdatePasswordRequest;
import com.example.newsfeedproject.user.dto.UpdateUserInfoRequest;
import com.example.newsfeedproject.user.dto.UserResponse;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 내 프로필 조회
     **/
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserInfo(
            @LoginUserResolver User user
    ) {
        UserResponse response = userService.getUserInfo(user.getId());
        return ResponseEntity.ok(response);
    }

    /**
     * 유저 프로필 조회
     **/
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserInfo(
            @PathVariable Long userId
    ) {
        UserResponse response = userService.getUserInfo(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 유저 정보 수정
     **/
    @PatchMapping
    public ResponseEntity<UserResponse> updateUserInfo(
            @LoginUserResolver User user,
            @RequestBody @Valid UpdateUserInfoRequest request
    ) {
        UserResponse response = userService.updateUserInfo(user.getId(), request);
        return ResponseEntity.ok(response);
    }

    /**
     * 비밀번호 변경
     **/
    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @LoginUserResolver User user,
            @RequestBody @Valid UpdatePasswordRequest request
    ) {
        userService.updatePassword(user.getId(), request);
        return ResponseEntity.ok().build();
    }
}
