package com.example.newsfeedproject.domain.auth.controller;

import com.example.newsfeedproject.common.annotation.LoginUserResolver;
import com.example.newsfeedproject.common.dto.GlobalApiResponse;
import com.example.newsfeedproject.domain.auth.service.AuthService;
import com.example.newsfeedproject.domain.user.dto.DeleteUserRequest;
import com.example.newsfeedproject.domain.user.dto.LoginRequest;
import com.example.newsfeedproject.domain.user.dto.SignupRequest;
import com.example.newsfeedproject.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public GlobalApiResponse<?> signup(@Valid @RequestBody SignupRequest request) {

        authService.signup(request);

        return GlobalApiResponse.success(HttpStatus.CREATED, "회원 가입이 완료되었습니다.", null);
    }

    // 로그인
    @PostMapping("/login")
    public GlobalApiResponse<?> login(@Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return GlobalApiResponse.ok("로그인 되었습니다.", token);
    }

    // 로그아웃
    @PostMapping("/logout")
    public GlobalApiResponse<?> logout() {

        return GlobalApiResponse.ok("로그아웃 되었습니다.", null);
    }

    // 회원탈퇴
    @DeleteMapping("/withdraw")
    public GlobalApiResponse<?> withdraw(@LoginUserResolver User user,
                                         @Valid @RequestBody DeleteUserRequest request) {

        authService.withdraw(user, request);

        return GlobalApiResponse.ok("회원탈퇴 되었습니다.", null);
    }
}
