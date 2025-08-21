package com.example.newsfeedproject.domain.user.mapper;

import com.example.newsfeedproject.domain.user.dto.SignupRequest;
import com.example.newsfeedproject.domain.user.dto.UserResponse;
import com.example.newsfeedproject.domain.user.entity.User;
import org.mapstruct.Mapper;

/**
 * User 관련 매핑 처리를 위한 Mapper 클래스
 * toEntity : SignupRequest DTO를 User 엔티티로 변환
 * toResponse : User 엔티티를 UserResponse DTO로 변환
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    // SignupRequest DTO -> User Entity
    User toEntity(SignupRequest signupRequest);

    // User Entity -> UserResponse DTO
    UserResponse toResponse(User user);
}

// [ 서비스 레이어 ]
// @RequiredArgsConstructor
// private final UserMapper userMapper;

// request -> entity 또는 entity -> response로 변환시
// userMapper.toEntity(signupRequest) 또는 userMapper.toResponse(user)