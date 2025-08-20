package com.example.newsfeedproject.comment.service;

import com.example.newsfeedproject.comment.dto.CommentCreateResponse;
import com.example.newsfeedproject.comment.dto.CommentListResponse;
import com.example.newsfeedproject.comment.dto.CommentRequest;
import com.example.newsfeedproject.comment.dto.CommentUpdateResponse;
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

import java.util.List;
import java.util.stream.Collectors;

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
        commentRepository.save(comment);

        return commentMapper.toCreateResponse(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentListResponse> getComments(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findAllByPostOrderByCreatedAtDesc(post);
        List<CommentListResponse> commentListResponse = comments.stream()
                .map(commentMapper::toListResponse)
                .collect(Collectors.toList());

        return commentListResponse;
    }

    @Transactional
    public CommentUpdateResponse updateComment(Long commentId, CommentRequest request, User user) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

        boolean isCommentAuthor = comment.getUser().getId().equals(user.getId());
        boolean isPostAuthor = comment.getPost().getUser().getId().equals(user.getId());

        // 댓글 작성자도 아니고 게시글 작성자도 아니면 예외 발생
        if (!isCommentAuthor && !isPostAuthor)
            throw new BusinessException(ErrorCode.FORBIDDEN_COMMENT);

        comment.updateContent(request.content());

        return commentMapper.toUpdateResponse(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

        boolean isCommentAuthor = comment.getUser().getId().equals(user.getId());
        boolean isPostAuthor = comment.getPost().getUser().getId().equals(user.getId());

        // 댓글 작성자도 아니고 게시글 작성자도 아니면 예외 발생
        if (!isCommentAuthor && !isPostAuthor)
            throw new BusinessException(ErrorCode.FORBIDDEN_COMMENT);

        commentRepository.delete(comment);
    }
}
