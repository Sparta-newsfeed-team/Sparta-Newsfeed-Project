package com.example.newsfeedproject.domain.comment.service;

import com.example.newsfeedproject.domain.comment.dto.CommentCreateResponse;
import com.example.newsfeedproject.domain.comment.dto.CommentListResponse;
import com.example.newsfeedproject.domain.comment.dto.CommentRequest;
import com.example.newsfeedproject.domain.comment.dto.CommentUpdateResponse;
import com.example.newsfeedproject.domain.comment.entity.Comment;
import com.example.newsfeedproject.domain.comment.repository.CommentRepository;
import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.domain.comment.mapper.CommentMapper;
import com.example.newsfeedproject.domain.post.entity.Post;
import com.example.newsfeedproject.domain.post.service.PostService;
import com.example.newsfeedproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostService postService;

    @Transactional
    public CommentCreateResponse createComment(Long postId, CommentRequest request, User user) {

        Post post = postService.findPostByIdOrElseThrow(postId);

        Comment comment = new Comment(request.content(), post, user);
        commentRepository.save(comment);

        return commentMapper.toCreateResponse(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentListResponse> getComments(Long postId) {

        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId);
        List<CommentListResponse> commentListResponse = comments.stream()
                .map(commentMapper::toListResponse)
                .collect(Collectors.toList());

        return commentListResponse;
    }

    @Transactional
    public CommentUpdateResponse updateComment(Long commentId, CommentRequest request, User user) {

        Comment comment = findByCommentId(commentId);

        validateAuthorOrPostAuthor(comment, user);

        comment.updateContent(request.content());

        return commentMapper.toUpdateResponse(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {

        Comment comment = findByCommentId(commentId);

        validateAuthorOrPostAuthor(comment, user);

        commentRepository.delete(comment);
    }

    private Comment findByCommentId(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

        return comment;
    }

    private void validateAuthorOrPostAuthor(Comment comment, User user) {

        boolean isCommentAuthor = comment.getUser().getId().equals(user.getId());
        boolean isPostAuthor = comment.getPost().getUser().getId().equals(user.getId());

        // 댓글 작성자도 아니고 게시글 작성자도 아니면 예외 발생
        if (!isCommentAuthor && !isPostAuthor)
            throw new BusinessException(ErrorCode.FORBIDDEN_COMMENT);
    }
}
