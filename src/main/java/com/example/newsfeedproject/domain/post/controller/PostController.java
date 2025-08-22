package com.example.newsfeedproject.domain.post.controller;

import com.example.newsfeedproject.common.annotation.LoginUserResolver;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 뉴스피드 게시물 생성
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostRequest request,
                                                   @LoginUserResolver User user) {

        PostResponse postResponse = postService.createPost(request, user);

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
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {

        PostResponse postResponseById = postService.getPostById(postId);

        return ResponseEntity.ok(postResponseById);
    }

    // 기간별 게시물 조회 (생성일 기준 최신순)
    @GetMapping("/search/period")
    public ResponseEntity<PostListResponse> getPostsByPeriod(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                             @RequestParam(defaultValue = "0") int page) {

        // LocalDate를 LocalDateTime으로 변환
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        PostListResponse postListResponseByPeriod = postService.getPostsByPeriod(startDateTime, endDateTime, pageable);

        return ResponseEntity.ok(postListResponseByPeriod);
    }

    // 해시태그별 게시물 조회 (생성일 기준 최신순)
    @GetMapping("/search/hashtag")
    public ResponseEntity<PostListResponse> getPostsByHashtag(@RequestParam String tag,
                                                              @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        PostListResponse response = postService.getPostsByHashtag(tag, pageable);
        return ResponseEntity.ok(response);
    }

     // 게시물 내용 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePostContent(@LoginUserResolver User user,
                                                          @PathVariable Long postId,
                                                          @Valid @RequestBody UpdatePostContentRequest request) {

        PostResponse updatePostContentResponse = postService.updatePostContent(user, postId, request);

        return ResponseEntity.ok(updatePostContentResponse);
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@LoginUserResolver User user,
                                           @PathVariable Long postId) {

        postService.deletePostById(user, postId);

        return ResponseEntity.noContent().build();
    }
}
