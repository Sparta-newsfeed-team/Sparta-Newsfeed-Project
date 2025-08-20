package com.example.newsfeedproject.post.controller;

import com.example.newsfeedproject.post.dto.PostListResponse;
import com.example.newsfeedproject.post.dto.PostRequest;
import com.example.newsfeedproject.post.dto.PostResponse;
import com.example.newsfeedproject.post.dto.UpdatePostContentRequest;
import com.example.newsfeedproject.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
     * 단건 게시물 조회
     **/
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@Valid @PathVariable Long postId) {

        PostResponse postResponseById = postService.getPostById(postId);

        return ResponseEntity.ok(postResponseById);
    }

    /**
     * 전체 또는 기간별 게시물 조회
     * 수정일 기준 최신순
     **/
    @GetMapping
    public ResponseEntity<PostListResponse> getPostsByPeriod(@RequestParam(required = false) LocalDateTime startDate,
                                                             @RequestParam(required = false) LocalDateTime endDate,
                                                             @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        PostListResponse postListResponse;

        if (startDate == null && endDate == null)
            postListResponse = postService.getPosts(pageable);
        else
            postListResponse = postService.getPostsByPeriod(startDate, endDate, pageable);

        return ResponseEntity.ok(postListResponse);
    }

    /**
     * 게시물 내용 수정
     **/
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePostContent(@PathVariable Long postId,
                                                          @Valid @RequestBody UpdatePostContentRequest updatePostContentRequest) {

        PostResponse updatePostContentResponse = postService.updatePostContent(postId, updatePostContentRequest);

        return ResponseEntity.ok(updatePostContentResponse);
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
