package com.example.newsfeedproject.domain.hashtag.repository;

import com.example.newsfeedproject.domain.hashtag.entity.PostHashtag;
import com.example.newsfeedproject.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {

    // 게시물 삭제 시 모든 매핑 데이터 삭제 (해당 게시글과 연결된 해시태그 정보 함께 삭제)
    void deleteAllByPost(Post post);
}
