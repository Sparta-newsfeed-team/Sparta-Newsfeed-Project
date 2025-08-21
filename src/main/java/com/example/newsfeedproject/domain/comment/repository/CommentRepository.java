package com.example.newsfeedproject.domain.comment.repository;

import com.example.newsfeedproject.domain.comment.entity.Comment;
import com.example.newsfeedproject.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostOrderByCreatedAtDesc(Post post);
}
