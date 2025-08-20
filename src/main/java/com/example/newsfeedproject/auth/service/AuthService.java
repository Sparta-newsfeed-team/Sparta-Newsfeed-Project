package com.example.newsfeedproject.auth.service;

import com.example.newsfeedproject.common.config.PasswordEncoder;
import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.mapper.UserMapper;
import com.example.newsfeedproject.user.dto.DeleteUserRequest;
import com.example.newsfeedproject.user.dto.SignupRequest;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public void signup(SignupRequest signupRequest) {

        // 탈퇴한 유저의 이메일도 함께 처리
        if (userRepository.findByEmail(signupRequest.email()).isPresent())
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);

        User user = userMapper.toEntity(signupRequest);
        String encodedPassword = passwordEncoder.encode(signupRequest.password());
        user.updatePassword(encodedPassword);

        userRepository.save(user);
    }

    @Transactional
    public void withdraw(String email, DeleteUserRequest deleteUserRequest) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(user.getPassword(), deleteUserRequest.password()))
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);

        // 유저 논리적 삭제
        user.markAsWithdrawn();

        userRepository.save(user);
    }
}
