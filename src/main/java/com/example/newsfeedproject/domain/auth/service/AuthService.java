package com.example.newsfeedproject.domain.auth.service;

import com.example.newsfeedproject.common.config.PasswordEncoder;
import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.common.security.JwtUtil;
import com.example.newsfeedproject.domain.user.dto.LoginRequest;
import com.example.newsfeedproject.domain.user.mapper.UserMapper;
import com.example.newsfeedproject.domain.user.dto.DeleteUserRequest;
import com.example.newsfeedproject.domain.user.dto.SignupRequest;
import com.example.newsfeedproject.domain.user.entity.User;
import com.example.newsfeedproject.domain.user.service.UserServiceApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final UserServiceApi userService;

    @Transactional
    public void signup(SignupRequest signupRequest) {

        // 탈퇴한 유저의 이메일을 포함하여 중복되는 이메일이 있는 경우 예외 처리
        userService.checkExistsUserByEmail(signupRequest.email());

        User user = userMapper.toEntity(signupRequest);
        String encodedPassword = passwordEncoder.encode(signupRequest.password());
        user.updatePassword(encodedPassword);

        userService.saveUser(user);
    }

    @Transactional
    public String login(LoginRequest loginRequest) {

        User user = userService.findUserByEmail(loginRequest.email());

        // 사용할 수 있는 계정 확인 -> 비밀번호 확인
        throwIfUserIsNotUsable(user);
        throwIfPasswordMismatch(loginRequest.password(), user.getPassword());

        return jwtUtil.createToken(user.getId());
    }

    @Transactional
    public void withdraw(User user, DeleteUserRequest deleteUserRequest) {

        // 비밀번호 확인 후 유저 논리적 삭제
        throwIfPasswordMismatch(deleteUserRequest.password(), user.getPassword());
        user.markAsWithdrawn();

        userService.saveUser(user);
    }

    public void throwIfPasswordMismatch(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword))
            throw new BusinessException(ErrorCode.PASSWORD_INCORRECT);
    }

    public void throwIfUserIsNotUsable(User user) {
        if (!user.isUsable())
            throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
    }
}
