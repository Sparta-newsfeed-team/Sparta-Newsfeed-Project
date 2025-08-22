package com.example.newsfeedproject.domain.hashtag.repository;

import com.example.newsfeedproject.domain.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    // 태그 이름으로 해시태그를 찾기 위한 메소드
    Optional<Hashtag> findByName(String name);
}
