package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.service.UserService;
import com.example.newsfeedproject.user.dto.DeleteUserRequest;
import com.example.newsfeedproject.user.dto.UserResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.newsfeedproject.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    /**
     * 회원가입
     **/
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpRequest request) {
        userService.signup(request);

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
        userService.withdraw(user.getEmail(), request);

        return ResponseEntity.noContent().build();
    }
}
