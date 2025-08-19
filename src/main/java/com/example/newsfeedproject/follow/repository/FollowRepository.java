package com.example.newsfeedproject.follow.repository;

import com.example.newsfeedproject.follow.entity.Follow;
import com.example.newsfeedproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByUserAndFollowingUser(User user, User followingUser);

}
