package com.example.newsfeedproject.post.repository;

import com.example.newsfeedproject.post.entity.Post;
import com.example.newsfeedproject.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    /**
     * 특정 유저 목록(List<User>)에 포함된 모든 게시물을 페이징하여 조회
     */
    Page<Post> findByUserIn(List<User> users, Pageable pageable);
}
