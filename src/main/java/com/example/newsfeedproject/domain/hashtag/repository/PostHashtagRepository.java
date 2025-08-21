package com.example.newsfeedproject.domain.hashtag.repository;

import com.example.newsfeedproject.domain.hashtag.entity.PostHashtag;
import com.example.newsfeedproject.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {

}
