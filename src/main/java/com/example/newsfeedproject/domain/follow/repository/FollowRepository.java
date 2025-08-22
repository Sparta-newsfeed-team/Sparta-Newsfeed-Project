package com.example.newsfeedproject.domain.follow.repository;

import com.example.newsfeedproject.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowingUserIdAndUserId(Long followingUserId, Long userId);

    void deleteByFollowingUserIdAndUserId(Long followingUserId, Long userId);

    // 팔로잉 목록 조회
    @EntityGraph(attributePaths = {"followingUser"})
    List<Follow> findAllByUserId(Long userId);

    // 팔로우 목록 조회
    @EntityGraph(attributePaths = {"user"})
    List<Follow> findAllByFollowingUserId(Long followingUserId);
}
