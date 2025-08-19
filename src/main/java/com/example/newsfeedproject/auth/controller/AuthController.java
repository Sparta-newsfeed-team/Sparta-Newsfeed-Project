package com.example.newsfeedproject.auth.controller;

import com.example.newsfeedproject.auth.service.AuthService;
import com.example.newsfeedproject.user.dto.DeleteUserRequest;
import com.example.newsfeedproject.user.dto.SignupRequest;
import com.example.newsfeedproject.user.dto.UserResponse;
import com.example.newsfeedproject.user.entity.User;
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

    /**
     * 회원가입
     **/
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    /**
     * 회원 탈퇴
     **/
    @DeleteMapping("/withdraw")
    public ResponseEntity<UserResponse> withdraw(
            @SessionAttribute(name = "LOGIN_USER", required = false) User user,
            @Valid @RequestBody DeleteUserRequest request) {

        // 로그인 상태 체크
        if (user == null) {
            // 로그인 되지 않으면 상태 코드 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 로그인된 사용자인 경우, 서비스 로직 호출
        authService.withdraw(user.getEmail(), request);

        return ResponseEntity.noContent().build();
    }
}
