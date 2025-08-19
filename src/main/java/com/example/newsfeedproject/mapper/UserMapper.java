package com.example.newsfeedproject.mapper;

import com.example.newsfeedproject.user.dto.SignupRequest;
import com.example.newsfeedproject.user.dto.UserResponse;
import com.example.newsfeedproject.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SignupRequest signupRequest);
    UserResponse toResponse(User user);
}

// [ 서비스 레이어 ]
// @RequiredArgsConstructor
// private final UserMapper userMapper;

// request -> entity 또는 entity -> response로 변환시
// userMapper.toEntity(signupRequest) 또는 userMapper.toResponse(user)