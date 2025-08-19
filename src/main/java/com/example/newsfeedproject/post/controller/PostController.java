package com.example.newsfeedproject.post.controller;

import com.example.newsfeedproject.post.dto.PostRequest;
import com.example.newsfeedproject.post.dto.PostResponse;
import com.example.newsfeedproject.post.dto.UpdatePostContentRequest;
import com.example.newsfeedproject.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    /**
     * 뉴스피드 게시물 생성
     **/
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    /**
     * 전체 게시물 조회
     **/
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getPosts(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        return ResponseEntity.ok(postService.getPosts(pageable));
    }

    /**
     * 단건 게시물 조회
     **/
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@Valid @PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    /**
     * 게시물 내용 수정
     **/
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePostContent(@PathVariable Long postId, @Valid @RequestBody UpdatePostContentRequest updatePostContentRequest) {
        return ResponseEntity.ok(postService.updatePostContent(postId, updatePostContentRequest));
    }

    /**
     * 게시물 삭제
     **/
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePostById(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("게시물이 삭제되었습니다.");
    }
}
