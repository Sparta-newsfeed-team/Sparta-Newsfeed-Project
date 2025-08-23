package com.example.newsfeedproject.domain.auth.controller;

import com.example.newsfeedproject.common.annotation.LoginUserResolver;
import com.example.newsfeedproject.common.dto.GlobalApiResponse;
import com.example.newsfeedproject.domain.auth.service.AuthService;
import com.example.newsfeedproject.domain.user.dto.DeleteUserRequest;
import com.example.newsfeedproject.domain.user.dto.LoginRequest;
import com.example.newsfeedproject.domain.user.dto.SignupRequest;
import com.example.newsfeedproject.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public GlobalApiResponse<?> logout(HttpServletRequest httpServletRequest) {

        sessionLogoutApply(httpServletRequest);

        return GlobalApiResponse.ok("로그아웃 되었습니다.", null);
    }

    // 회원탈퇴
    @DeleteMapping("/withdraw")
    public GlobalApiResponse<?> withdraw(@LoginUserResolver User user,
                                         @Valid @RequestBody DeleteUserRequest request,
                                         HttpServletRequest httpServletRequest) {

        // 로그인된 사용자인 경우, 서비스 로직 호출한 뒤 로그아웃 적용
        authService.withdraw(user, request);
        sessionLogoutApply(httpServletRequest);

        return GlobalApiResponse.ok("회원탈퇴 되었습니다.", null);
    }

    /**
     * 로그아웃 또는 회원 탈퇴시 세션 삭제
     */
    public void sessionLogoutApply(HttpServletRequest httpServletRequest) {

        HttpSession httpSession = httpServletRequest.getSession(false); // 세션 생성 금지

        // 로그인이 되어있는 경우 세션 삭제
        if (httpSession != null)
            httpSession.invalidate();
    }
}