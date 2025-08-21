package com.example.newsfeedproject.domain.comment.mapper;

import com.example.newsfeedproject.domain.comment.dto.CommentCreateResponse;
import com.example.newsfeedproject.domain.comment.dto.CommentListResponse;
import com.example.newsfeedproject.domain.comment.dto.CommentUpdateResponse;
import com.example.newsfeedproject.domain.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Comment 관련 매핑 처리를 위한 Mapper 클래스
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {

    // Comment Entity -> CommentCreateResponse DTO
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "username")
    CommentCreateResponse toCreateResponse(Comment comment);

    // Comment Entity -> CommentUpdateResponse DTO
    CommentUpdateResponse toUpdateResponse(Comment comment);

    // Comment Entity -> CommentListResponse DTO
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "username")
    CommentListResponse toListResponse(Comment comment);


}
