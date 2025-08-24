package com.example.newsfeedproject.domain.auth.controller;

import com.example.newsfeedproject.common.annotation.LoginUserResolver;
import com.example.newsfeedproject.common.dto.GlobalApiResponse;
import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.common.security.CookieUtil;
import com.example.newsfeedproject.common.security.JwtUtil;
import com.example.newsfeedproject.domain.auth.dto.TokenResponse;
import com.example.newsfeedproject.domain.auth.service.AuthService;
import com.example.newsfeedproject.domain.user.dto.DeleteUserRequest;
import com.example.newsfeedproject.domain.user.dto.LoginRequest;
import com.example.newsfeedproject.domain.user.dto.SignupRequest;
import com.example.newsfeedproject.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    // 회원가입
    @PostMapping("/signup")
    public GlobalApiResponse<?> signup(@Valid @RequestBody SignupRequest request) {

        authService.signup(request);

        return GlobalApiResponse.success(HttpStatus.CREATED, "회원 가입이 완료되었습니다.", null);
    }

    // 로그인
    @PostMapping("/login")
    public GlobalApiResponse<?> login(@Valid @RequestBody LoginRequest request,
                                      HttpServletResponse response) {

        TokenResponse tokens = authService.login(request);
        cookieUtil.addHttpOnlyCookie(response, tokens.refreshToken());

        return GlobalApiResponse.ok("로그인 되었습니다.", tokens.accessToken());
    }

    // 로그아웃
    @PostMapping("/logout")
    public GlobalApiResponse<?> logout(@LoginUserResolver User user,
                                       HttpServletResponse response) {

        cookieUtil.deleteCookie(response);
        return GlobalApiResponse.ok("로그아웃 되었습니다.", null);
    }

    // 회원탈퇴
    @DeleteMapping("/withdraw")
    public GlobalApiResponse<?> withdraw(@LoginUserResolver User user,
                                         @Valid @RequestBody DeleteUserRequest request,
                                         HttpServletResponse response) {

        authService.withdraw(user, request);

        return GlobalApiResponse.ok("회원탈퇴 되었습니다.", null);
    }

    @PostMapping("/refresh")
    public GlobalApiResponse<?> refresh(HttpServletRequest request) {
        String refreshToken = cookieUtil.getCookieValue(request);
        if (refreshToken == null)
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);

        long userId = jwtUtil.getUserIdFromToken(refreshToken);
        String newAccessToken = jwtUtil.createAccessToken(userId);

        return GlobalApiResponse.ok("토큰 재발급 완료", newAccessToken);
    }
}
