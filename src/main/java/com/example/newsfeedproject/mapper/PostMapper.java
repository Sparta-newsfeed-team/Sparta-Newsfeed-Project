package com.example.newsfeedproject.mapper;

import com.example.newsfeedproject.post.dto.PostRequest;
import com.example.newsfeedproject.post.dto.PostResponse;
import com.example.newsfeedproject.post.entity.Post;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toEntity(PostRequest postRequest);
    @Named("toResponse(Post)")
    PostResponse toResponse(Post post);
    @IterableMapping(qualifiedByName = "toResponse(Post)")
    List<PostResponse> toListResponse(Page<Post> posts);
}
