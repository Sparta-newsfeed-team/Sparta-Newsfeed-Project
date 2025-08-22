package com.example.newsfeedproject.domain.comment.controller;

import com.example.newsfeedproject.common.dto.GlobalApiResponse;
import com.example.newsfeedproject.domain.comment.dto.CommentCreateResponse;
import com.example.newsfeedproject.domain.comment.dto.CommentListResponse;
import com.example.newsfeedproject.domain.comment.dto.CommentRequest;
import com.example.newsfeedproject.domain.comment.dto.CommentUpdateResponse;
import com.example.newsfeedproject.domain.comment.service.CommentService;
import com.example.newsfeedproject.common.annotation.LoginUserResolver;
import com.example.newsfeedproject.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성 기능
    @PostMapping
    public GlobalApiResponse<CommentCreateResponse> createComment(@PathVariable Long postId,
                                                                  @RequestBody @Valid CommentRequest commentRequest,
                                                                  @LoginUserResolver User user) {

        CommentCreateResponse commentCreateResponse = commentService.createComment(postId, commentRequest, user);

        return GlobalApiResponse.success(HttpStatus.CREATED, "댓글이 생성되었습니다.", commentCreateResponse);
    }

    // 댓글 조회 기능
    @GetMapping
    public GlobalApiResponse<List<CommentListResponse>> getComments(@PathVariable Long postId) {

        List<CommentListResponse> commentListResponse = commentService.getComments(postId);

        return GlobalApiResponse.ok("게시글 댓글이 조회되었습니다.", commentListResponse);
    }

    // 댓글 수정 기능
    @PatchMapping("/{commentId}")
    public GlobalApiResponse<CommentUpdateResponse> updateComment(@PathVariable Long postId,
                                                                  @PathVariable Long commentId,
                                                                  @RequestBody @Valid CommentRequest commentRequest,
                                                                  @LoginUserResolver User user) {

        CommentUpdateResponse commentUpdateResponse = commentService.updateComment(commentId, commentRequest, user);

        return GlobalApiResponse.ok("댓글이 수정되었습니다.", commentUpdateResponse);
    }

    // 댓글 삭제 기능
    @DeleteMapping("/{commentId}")
    public GlobalApiResponse<?> deleteComment(@PathVariable Long postId,
                                              @PathVariable Long commentId,
                                              @LoginUserResolver User user) {

        commentService.deleteComment(commentId, user);

        return GlobalApiResponse.success(HttpStatus.NO_CONTENT, "댓글이 삭제되었습니다.", null);

    }
}
