package com.example.newsfeedproject.auth.service;

import com.example.newsfeedproject.common.config.PasswordEncoder;
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
        if(userRepository.findByEmail(signupRequest.email()).isPresent())
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");

        User user = userMapper.toEntity(signupRequest);
        user.updatePassword(passwordEncoder.encode(signupRequest.password()));
        userRepository.save(user);
    }

    @Transactional
    public void withdraw(String email, DeleteUserRequest deleteUserRequest) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        if(!passwordEncoder.matches(user.getPassword(), deleteUserRequest.password()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        user.markAsWithdrawn();
        userRepository.save(user);
    }
}
