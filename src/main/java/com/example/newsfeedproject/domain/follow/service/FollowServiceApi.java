package com.example.newsfeedproject.domain.follow.service;

import com.example.newsfeedproject.domain.follow.entity.Follow;
import com.example.newsfeedproject.domain.user.entity.User;

import java.util.List;

public interface FollowServiceApi {

    /**
     * 팔로우한 모든 User 조회
     */
    List<Follow> findAllUserByUser(User user);
}
