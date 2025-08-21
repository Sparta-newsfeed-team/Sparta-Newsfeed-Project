package com.example.newsfeedproject.domain.follow.mapper;

import com.example.newsfeedproject.domain.follow.dto.FollowerResponse;
import com.example.newsfeedproject.domain.follow.dto.FollowingResponse;
import com.example.newsfeedproject.domain.follow.entity.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Follow 관련 매핑 처리를 위한 Mapper 클래스
 * toFollowingResponse : 팔로잉 목록 조회시 Follow → FollowingResponse 변환
 * toFollowerResponse : 팔로워 목록 조회시 Follow → FollowerResponse 변환
 */
@Mapper(componentModel = "spring")
public interface FollowMapper {

    // Follow Entity -> FollowingResponse DTO
    @Mapping(source = "followingUser.id", target = "id")
    @Mapping(source = "followingUser.name", target = "name")
    FollowingResponse toFollowingResponse(Follow follow);

    // Follow Entity -> FollowerResponse DTO
    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.name", target = "name")
    FollowerResponse toFollowerResponse(Follow follow);
}