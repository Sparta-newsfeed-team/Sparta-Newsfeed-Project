package com.example.newsfeedproject.mapper;

import com.example.newsfeedproject.follow.dto.FollowingResponse;
import com.example.newsfeedproject.follow.entity.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    @Mapping(source = "followingUser.id", target = "id")
    @Mapping(source = "followingUser.name", target = "name")
    FollowingResponse toResponse(Follow follow);
}