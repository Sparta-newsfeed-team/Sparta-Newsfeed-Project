package com.example.newsfeedproject.mapper;

import com.example.newsfeedproject.follow.dto.FollowerResponse;
import com.example.newsfeedproject.follow.dto.FollowingResponse;
import com.example.newsfeedproject.follow.entity.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FollowMapper {

    // 팔로잉 목록 조회 Mapping
    @Mapping(source = "followingUser.id", target = "id")
    @Mapping(source = "followingUser.name", target = "name")
    FollowingResponse toResponse(Follow follow);

    // 팔로워 목록 조회
    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.name", target = "name")
    FollowerResponse toFollowerResponse(Follow follow);
}