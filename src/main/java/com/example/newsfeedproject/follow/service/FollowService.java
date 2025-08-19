package com.example.newsfeedproject.follow.service;

import com.example.newsfeedproject.follow.dto.FollowerResponse;
import com.example.newsfeedproject.follow.dto.FollowingResponse;
import com.example.newsfeedproject.follow.entity.Follow;
import com.example.newsfeedproject.follow.repository.FollowRepository;
import com.example.newsfeedproject.mapper.FollowMapper;
import com.example.newsfeedproject.mapper.UserMapper;
import com.example.newsfeedproject.user.dto.UserResponse;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final FollowMapper followMapper;


    /** 팔로우 로직 **/
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

    /** 언팔로우 로직 **/
    @Transactional
    public void unfollowUser(Long followingUserId, User user) {
        // 언팔로우 할 대상 조회
        User followingUser = userRepository.findById(followingUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        // 로그인 유저와 언팔로우 대상 유저 사이가 팔로우 한 관계인지 조회
        Follow follow = followRepository.findByUserAndFollowingUser(user, followingUser)
                        .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 아닙니다."));

        // 팔로우 관계 삭제
        followRepository.delete(follow);
    }

    /** 팔로잉 목록 조회 로직 **/
    @Transactional(readOnly = true)
    public List<FollowingResponse> getFollowingList(User user) {
        // 로그인한 유저가 팔로우한 모든 팔로잉 조회
        List<Follow> followList = followRepository.findAllByUser(user);

        // 조회한 리스트를 순회하며 DTO 변환
        return followList.stream()
                .map(followMapper::toResponse)
                .collect(Collectors.toList());
    }

    /** 팔로워 목록 조회 로직 **/
    @Transactional(readOnly = true)
    public List<FollowerResponse> getFollowerList(User user) {
        // 로그인한 유저를 팔로우하는 모든 팔로워 조회
        List<Follow> followerList = followRepository.findAllByFollowingUser(user);

        // 조회한 리스트를 순회하며 DTO 변환
        return followerList.stream()
                .map(followMapper::toFollowerResponse)
                .collect(Collectors.toList());


    }
}
