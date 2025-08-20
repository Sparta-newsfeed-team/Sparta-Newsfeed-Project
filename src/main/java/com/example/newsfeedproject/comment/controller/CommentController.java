package com.example.newsfeedproject.comment.controller;

import com.example.newsfeedproject.comment.dto.CommentCreateResponse;
import com.example.newsfeedproject.comment.dto.CommentRequest;
import com.example.newsfeedproject.comment.service.CommentService;
import com.example.newsfeedproject.common.annoation.LoginUserResolver;
import com.example.newsfeedproject.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentCreateResponse> createComment(@PathVariable Long postId,
                                                               @RequestBody @Valid CommentRequest commentRequest,
                                                               @LoginUserResolver User user) {

        CommentCreateResponse commentCreateResponse = commentService.createComment(postId, commentRequest, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentCreateResponse);
    }
}
