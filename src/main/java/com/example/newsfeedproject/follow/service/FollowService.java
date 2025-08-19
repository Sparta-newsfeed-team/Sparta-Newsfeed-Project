package com.example.newsfeedproject.follow.service;

import com.example.newsfeedproject.follow.entity.Follow;
import com.example.newsfeedproject.follow.repository.FollowRepository;
import com.example.newsfeedproject.mapper.UserMapper;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public void followUser(Long followingUserId, User user) {
        // 자기 자신을 팔로우 하는지 확인하는 로직
        if (user.getId().equals(followingUserId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        // 팔로우 할 대상 유저 확인
        User followingUser = userRepository.findById(followingUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        // 이미 팔로우 한 유저인지 확인
        followRepository.findByUserAndFollowingUser(user, followingUser).ifPresent(m -> {
            throw new IllegalArgumentException("이미 팔로우 한 유저입니다.");
        });

        Follow follow = new Follow(user, followingUser);
        followRepository.save(follow);
    }



}
