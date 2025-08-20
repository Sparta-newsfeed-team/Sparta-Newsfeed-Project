package com.example.newsfeedproject.comment.service;

import com.example.newsfeedproject.comment.dto.CommentCreateResponse;
import com.example.newsfeedproject.comment.dto.CommentRequest;
import com.example.newsfeedproject.comment.entity.Comment;
import com.example.newsfeedproject.comment.repository.CommentRepository;
import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.mapper.CommentMapper;
import com.example.newsfeedproject.post.entity.Post;
import com.example.newsfeedproject.post.repository.PostRepository;
import com.example.newsfeedproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentCreateResponse createComment(Long postId, CommentRequest request, User user) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        Comment comment = new Comment(request.content(), post, user);

        return commentMapper.toCreateResponse(comment);
    }
}
