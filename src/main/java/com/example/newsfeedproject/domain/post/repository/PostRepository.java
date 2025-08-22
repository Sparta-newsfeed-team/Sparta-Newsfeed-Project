package com.example.newsfeedproject.domain.post.repository;

import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.domain.post.entity.Post;
import com.example.newsfeedproject.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    /**
     * 특정 유저 목록(List<User>)에 포함된 모든 게시물을 페이징하여 조회
     */
    Page<Post> findByUserIn(List<User> users, Pageable pageable);

    /**
     * 특정 해시태그 ID를 가진 모든 게시물을 페이징하여 조회
     */
    @Query("SELECT p FROM Post p JOIN PostHashtag ph ON p.id = ph.post.id WHERE ph.hashtag.id = :hashtagId")
    Page<Post> findByHashtagId(@Param("hashtagId") Long hashtagId, Pageable pageable);
}
