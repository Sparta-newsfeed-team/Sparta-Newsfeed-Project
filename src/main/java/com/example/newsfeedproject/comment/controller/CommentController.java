package com.example.newsfeedproject.comment.controller;

import com.example.newsfeedproject.comment.dto.CommentCreateResponse;
import com.example.newsfeedproject.comment.dto.CommentListResponse;
import com.example.newsfeedproject.comment.dto.CommentRequest;
import com.example.newsfeedproject.comment.dto.CommentUpdateResponse;
import com.example.newsfeedproject.comment.service.CommentService;
import com.example.newsfeedproject.common.annoation.LoginUserResolver;
import com.example.newsfeedproject.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성 기능
    @PostMapping
    public ResponseEntity<CommentCreateResponse> createComment(@PathVariable Long postId,
                                                               @RequestBody @Valid CommentRequest commentRequest,
                                                               @LoginUserResolver User user) {

        CommentCreateResponse commentCreateResponse = commentService.createComment(postId, commentRequest, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentCreateResponse);
    }

    // 댓글 조회 기능
    @GetMapping
    public ResponseEntity<List<CommentListResponse>> getComments(@PathVariable Long postId) {

        List<CommentListResponse> commentListResponse = commentService.getComments(postId);

        return ResponseEntity.ok(commentListResponse);
    }

    // 댓글 수정 기능
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentUpdateResponse> updateComment(@PathVariable Long postId,
                                                               @PathVariable Long commentId,
                                                               @RequestBody @Valid CommentRequest commentRequest,
                                                               @LoginUserResolver User user) {

        CommentUpdateResponse commentUpdateResponse = commentService.updateComment(commentId, commentRequest, user);

        return ResponseEntity.ok(commentUpdateResponse);
    }

    // 댓글 삭제 기능
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId,
                                                @PathVariable Long commentId,
                                                @LoginUserResolver User user) {

        commentService.deleteComment(commentId, user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("댓글이 삭제되었습니다.");

    }
}
