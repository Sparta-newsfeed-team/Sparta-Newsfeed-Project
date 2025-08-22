package com.example.newsfeedproject.domain.follow.service;

import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.domain.follow.dto.FollowerResponse;
import com.example.newsfeedproject.domain.follow.dto.FollowingResponse;
import com.example.newsfeedproject.domain.follow.entity.Follow;
import com.example.newsfeedproject.domain.follow.mapper.FollowMapper;
import com.example.newsfeedproject.domain.follow.repository.FollowRepository;
import com.example.newsfeedproject.domain.user.entity.User;
import com.example.newsfeedproject.domain.user.service.UserServiceApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService implements FollowServiceApi {

    private final FollowRepository followRepository;
    private final FollowMapper followMapper;
    private final UserServiceApi userService;

    @Transactional
    public void followUser(Long followingUserId, User user) {

        // 자기 자신을 팔로우 하는지 확인
        if (user.getId().equals(followingUserId))
            throw new BusinessException(ErrorCode.CANNOT_FOLLOW_SELF);

        // 이미 팔로우 한 유저인지 확인
        boolean exists = followRepository.existsByFollowingUserIdAndUserId(followingUserId, user.getId());
        if (exists)
            throw new BusinessException(ErrorCode.ALREADY_FOLLOWED);

        User followingUser = userService.findUserByIdOrElseThrow(followingUserId);
        Follow follow = new Follow(user, followingUser);

        followRepository.save(follow);
    }

    @Transactional
    public void unfollowUser(Long followingUserId, User user) {

        // 로그인 유저와 언팔로우 대상 유저 사이가 팔로우 한 관계인지 조회
        boolean exists = followRepository.existsByFollowingUserIdAndUserId(followingUserId, user.getId());
        if (!exists)
            throw new BusinessException(ErrorCode.IS_NOT_FOLLOWING);

        // 팔로우 관계 삭제
        followRepository.deleteByFollowingUserIdAndUserId(followingUserId, user.getId());
    }

    @Transactional(readOnly = true)
    public List<FollowingResponse> getFollowingList(User user) {

        // 로그인한 유저가 팔로우한 모든 팔로잉 조회
        List<Follow> followList = followRepository.findAllByUserId(user.getId());

        // 조회한 리스트를 순회하며 Response DTO 변환
        List<FollowingResponse> followingResponses = followList.stream()
                .map(followMapper::toFollowingResponse)
                .collect(Collectors.toList());

        return followingResponses;
    }

    @Transactional(readOnly = true)
    public List<FollowerResponse> getFollowerList(User user) {

        // 로그인한 유저를 팔로우하는 모든 팔로워 조회
        List<Follow> followerList = followRepository.findAllByFollowingUserId(user.getId());

        // 조회한 리스트를 순회하며 Response DTO 변환
        List<FollowerResponse> followerResponses = followerList.stream()
                .map(followMapper::toFollowerResponse)
                .collect(Collectors.toList());

        return followerResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Follow> findAllUserByUser(User user) {

        return followRepository.findAllByUserId(user.getId());
    }
}
