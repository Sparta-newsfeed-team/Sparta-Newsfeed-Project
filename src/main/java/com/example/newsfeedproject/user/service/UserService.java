package com.example.newsfeedproject.user.service;

import com.example.newsfeedproject.common.config.PasswordEncoder;
import com.example.newsfeedproject.mapper.UserMapper;
import com.example.newsfeedproject.user.dto.UpdatePasswordRequest;
import com.example.newsfeedproject.user.dto.UpdateUserInfoRequest;
import com.example.newsfeedproject.user.dto.UserResponse;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.repository.UserRepository;
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

        if (request.isEmpty())
            throw new IllegalArgumentException("이름 또는 나이 중 하나는 반드시 입력되어야 합니다.");

        User targetUser = userRepository.findByIdOrElseThrow(id);
        targetUser.updateUserInfo(request.name(), request.age());

        return userMapper.toResponse(targetUser);
    }

    @Transactional
    public void updatePassword(Long id, UpdatePasswordRequest request) {

        User targetUser = userRepository.findByIdOrElseThrow(id);

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(request.currentPassword(), targetUser.getPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        // 동일 비밀번호 여부 확인
        if (passwordEncoder.matches(request.newPassword(), targetUser.getPassword()))
            throw new IllegalArgumentException("새 비밀번호는 현재 비밀번호와 달라야 합니다.");

        targetUser.updatePassword(passwordEncoder.encode(request.newPassword()));
    }
}
