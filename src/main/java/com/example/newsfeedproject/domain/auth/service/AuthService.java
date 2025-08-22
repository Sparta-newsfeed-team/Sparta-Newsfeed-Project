package com.example.newsfeedproject.domain.auth.service;

import com.example.newsfeedproject.common.config.PasswordEncoder;
import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
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
    public void withdraw(User user, DeleteUserRequest deleteUserRequest) {

        if (!passwordEncoder.matches(deleteUserRequest.password(), user.getPassword()))
            throw new BusinessException(ErrorCode.PASSWORD_INCORRECT);

        // 유저 논리적 삭제
        user.markAsWithdrawn();

        userService.saveUser(user);
    }
}
