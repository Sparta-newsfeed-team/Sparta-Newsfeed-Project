package com.example.newsfeedproject.domain.auth.controller;

import com.example.newsfeedproject.common.annotation.LoginUserResolver;
import com.example.newsfeedproject.domain.auth.service.AuthService;
import com.example.newsfeedproject.domain.user.dto.DeleteUserRequest;
import com.example.newsfeedproject.domain.user.dto.SignupRequest;
import com.example.newsfeedproject.domain.user.entity.User;
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

    //회원탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdraw(@LoginUserResolver User user,
                                           @Valid @RequestBody DeleteUserRequest request) {

        // 로그인된 사용자인 경우, 서비스 로직 호출
        authService.withdraw(user, request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("회원 탈퇴되었습니다.");
    }
}
