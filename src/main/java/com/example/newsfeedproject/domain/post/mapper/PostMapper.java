package com.example.newsfeedproject.domain.post.mapper;

import com.example.newsfeedproject.domain.post.dto.PostRequest;
import com.example.newsfeedproject.domain.post.dto.PostResponse;
import com.example.newsfeedproject.domain.post.entity.Post;
import com.example.newsfeedproject.domain.user.dto.AuthorResponse;
import com.example.newsfeedproject.domain.user.entity.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    // PostRequest DTO -> Post Entity
    Post toEntity(PostRequest postRequest, User user);

    // Post Entity -> PostResponse DTO
    @Mapping(source = "user", target = "author", qualifiedByName = "toAuthorResponse")
    @Named("toResponse(Post)")
    PostResponse toResponse(Post post);

    // User Entity -> AuthorResponse DTO
    @Named("toAuthorResponse")
    default AuthorResponse toAuthorResponse(User user) {
        return new AuthorResponse(user);
    }

    // Page<Post> -> List<PostResponse>
    @IterableMapping(qualifiedByName = "toResponse(Post)")
    List<PostResponse> toListResponse(Page<Post> posts);
}
