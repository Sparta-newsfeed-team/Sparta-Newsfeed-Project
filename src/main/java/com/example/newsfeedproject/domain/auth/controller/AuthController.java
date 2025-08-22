package com.example.newsfeedproject.domain.auth.controller;

import com.example.newsfeedproject.common.annotation.LoginUserResolver;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {

        authService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request,
                                        HttpServletRequest httpServletRequest) {

        Long userId = authService.login(request);
        HttpSession httpSession = httpServletRequest.getSession();

        httpSession.setAttribute("LOGIN_USER", userId);
        return ResponseEntity.status(HttpStatus.OK).body("로그인 되었습니다.");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {

        HttpSession httpSession = httpServletRequest.getSession(false); // 세션 생성 금지

        // 로그인이 되어있는 경우 세션 삭제
        if (httpSession != null)
            httpSession.invalidate();

        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 되었습니다.");
    }

    //회원탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdraw(@LoginUserResolver User user,
                                           @Valid @RequestBody DeleteUserRequest request) {

        // 로그인된 사용자인 경우, 서비스 로직 호출
        authService.withdraw(user, request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("회원 탈퇴되었습니다.");
    }
}