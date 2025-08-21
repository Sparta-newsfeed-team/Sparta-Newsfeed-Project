package com.example.newsfeedproject.domain.post.service;

import com.example.newsfeedproject.domain.post.entity.Post;

public interface PostServiceApi {

    /**
     * id 값으로 Post를 찾아서 반환
     * @param id post id
     */
    Post findPostByIdOrElseThrow(Long id);
}
