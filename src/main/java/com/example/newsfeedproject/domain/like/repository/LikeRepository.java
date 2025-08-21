package com.example.newsfeedproject.domain.like.repository;

import com.example.newsfeedproject.domain.like.entity.Like;
import com.example.newsfeedproject.domain.user.dto.PostUserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);

    List<Like> findByPostId(Long postId);
}
