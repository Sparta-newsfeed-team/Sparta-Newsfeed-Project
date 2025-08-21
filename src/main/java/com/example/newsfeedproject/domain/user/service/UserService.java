package com.example.newsfeedproject.domain.user.service;

import com.example.newsfeedproject.common.config.PasswordEncoder;
import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.domain.user.mapper.UserMapper;
import com.example.newsfeedproject.domain.user.dto.UpdatePasswordRequest;
import com.example.newsfeedproject.domain.user.dto.UpdateUserInfoRequest;
import com.example.newsfeedproject.domain.user.dto.UserResponse;
import com.example.newsfeedproject.domain.user.entity.User;
import com.example.newsfeedproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponse getUserInfo(Long id) {

        User foundUser = userRepository.findByIdOrElseThrow(id);

        return userMapper.toResponse(foundUser);
    }

    @Transactional
    public UserResponse updateUserInfo(Long id, UpdateUserInfoRequest request) {

        User targetUser = userRepository.findByIdOrElseThrow(id);
        targetUser.updateUserInfo(request.name(), request.age());

        return userMapper.toResponse(targetUser);
    }

    @Transactional
    public void updatePassword(Long id, UpdatePasswordRequest request) {

        User targetUser = userRepository.findByIdOrElseThrow(id);

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(request.currentPassword(), targetUser.getPassword()))
            throw new BusinessException(ErrorCode.PASSWORD_INCORRECT);

        // 동일 비밀번호 여부 확인
        if (passwordEncoder.matches(request.newPassword(), targetUser.getPassword()))
            throw new BusinessException(ErrorCode.PASSWORD_NOT_AVAILABLE);

        targetUser.updatePassword(passwordEncoder.encode(request.newPassword()));
    }
}
