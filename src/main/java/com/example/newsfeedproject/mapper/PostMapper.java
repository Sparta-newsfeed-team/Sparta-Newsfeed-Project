package com.example.newsfeedproject.mapper;

import com.example.newsfeedproject.post.dto.PostRequest;
import com.example.newsfeedproject.post.dto.PostResponse;
import com.example.newsfeedproject.post.entity.Post;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Post 관련 매핑 처리를 위한 Mapper 클래스
 * toEntity : PostRequest DTO를 Post 엔티티로 변환
 * toResponse : Post 엔티티를 PostResponse DTO로 변환
 * toListResponse : Page<Post>를 List<PostResponse>로 변환
 */
@Mapper(componentModel = "spring")
public interface PostMapper {

    // PostRequest DTO -> Post 엔티티
    Post toEntity(PostRequest postRequest);

    // Post Entity -> PostResponse DTO
    @Named("toResponse(Post)")
    PostResponse toResponse(Post post);

    // Page<Post> -> List<PostResponse>
    @IterableMapping(qualifiedByName = "toResponse(Post)")
    List<PostResponse> toListResponse(Page<Post> posts);
}
