package com.example.newsfeedproject.post.repository;

import com.example.newsfeedproject.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
