package com.example.newsfeedproject.follow.repository;

import com.example.newsfeedproject.follow.entity.Follow;
import com.example.newsfeedproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByUserAndFollowingUser(User user, User followingUser);

    // 팔로잉 목록 조회
    List<Follow> findAllByUser(User user);

    // 팔로우 목록 조회
    List<Follow> findAllByFollowingUser(User followingUser);
}
