package com.example.newsfeedproject.domain.post.controller;

import com.example.newsfeedproject.common.annoation.LoginUserResolver;
import com.example.newsfeedproject.domain.post.dto.PostListResponse;
import com.example.newsfeedproject.domain.post.dto.PostRequest;
import com.example.newsfeedproject.domain.post.dto.PostResponse;
import com.example.newsfeedproject.domain.post.dto.UpdatePostContentRequest;
import com.example.newsfeedproject.domain.post.service.PostService;
import com.example.newsfeedproject.domain.user.entity.User;
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

     // 뉴스피드 게시물 생성
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest) {

        PostResponse postResponse = postService.createPost(postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    // 뉴스피드 조회 (내가 팔로우한 사용자들의 게시물 최신순)
    @GetMapping("/feed")
    public ResponseEntity<PostListResponse> getNewsfeed(@LoginUserResolver User user,
                                                        @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());

        PostListResponse response = postService.getNewsfeed(user, pageable);

        return ResponseEntity.ok(response);
    }

    // 전체 게시물 조회
    @GetMapping
    public ResponseEntity<PostListResponse> getPosts(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "createdAt") String sortBy) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sortBy).descending());
        PostListResponse postListResponse = postService.getPosts(pageable);

        return ResponseEntity.ok(postListResponse);
    }

    // 단건 게시물 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@Valid @PathVariable Long postId) {

        PostResponse postResponseById = postService.getPostById(postId);

        return ResponseEntity.ok(postResponseById);
    }

     // 기간별 게시물 조회 (생성일 기준 최신순)
    @GetMapping("/search/period")
    public ResponseEntity<PostListResponse> getPostsByPeriod(@RequestParam LocalDateTime startDate,
                                                             @RequestParam LocalDateTime endDate,
                                                             @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        PostListResponse postListResponseByPeriod = postService.getPostsByPeriod(startDate, endDate, pageable);

        return ResponseEntity.ok(postListResponseByPeriod);
    }

     // 게시물 내용 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePostContent(@PathVariable Long postId,
                                                          @Valid @RequestBody UpdatePostContentRequest updatePostContentRequest) {

        PostResponse updatePostContentResponse = postService.updatePostContent(postId, updatePostContentRequest);

        return ResponseEntity.ok(updatePostContentResponse);
    }

     // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {

        postService.deletePostById(postId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("게시물이 삭제되었습니다.");
    }
}
