package com.example.newsfeedproject.domain.post.controller;

import com.example.newsfeedproject.common.annotation.LoginUserResolver;
import com.example.newsfeedproject.common.dto.GlobalApiResponse;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 뉴스피드 게시물 생성
    @PostMapping
    public GlobalApiResponse<PostResponse> createPost(@RequestBody @Valid PostRequest request,
                                                      @LoginUserResolver User user) {

        PostResponse postResponse = postService.createPost(request, user);

        return GlobalApiResponse.success(HttpStatus.CREATED, "게시글이 작성되었습니다.", postResponse);
    }

    // 뉴스피드 조회 (내가 팔로우한 사용자들의 게시물 최신순)
    @GetMapping("/feed")
    public GlobalApiResponse<PostListResponse> getNewsfeed(@LoginUserResolver User user,
                                                           @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());

        PostListResponse response = postService.getNewsfeed(user, pageable);

        return GlobalApiResponse.ok("뉴스피드가 성공적으로 조회되었습니다.", response);
    }

    // 전체 게시물 조회
    @GetMapping
    public GlobalApiResponse<PostListResponse> getPosts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "createdAt") String sortBy) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sortBy).descending());
        PostListResponse postListResponse = postService.getPosts(pageable);

        return GlobalApiResponse.ok("전체 게시글이 조회되었습니다.", postListResponse);
    }

    // 단건 게시물 조회
    @GetMapping("/{postId}")
    public GlobalApiResponse<PostResponse> getPost(@PathVariable Long postId) {

        PostResponse postResponseById = postService.getPostById(postId);

        return GlobalApiResponse.ok("단건 게시글이 조회되었습니다.", postResponseById);
    }

    // 기간별 게시물 조회 (생성일 기준 최신순)
    @GetMapping("/search/period")
    public GlobalApiResponse<PostListResponse> getPostsByPeriod(@RequestParam LocalDateTime startDate,
                                                                @RequestParam LocalDateTime endDate,
                                                                @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        PostListResponse postListResponseByPeriod = postService.getPostsByPeriod(startDate, endDate, pageable);

        return GlobalApiResponse.ok("기간별 게시글이 조회되었습니다.", postListResponseByPeriod);
    }

    // 해시태그별 게시물 조회 (생성일 기준 최신순)
    @GetMapping("/search/hashtag")
    public GlobalApiResponse<PostListResponse> getPostsByHashtag(@RequestParam String tag,
                                                                 @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        PostListResponse response = postService.getPostsByHashtag(tag, pageable);
        return GlobalApiResponse.ok("특정 해시태그 게시물이 조회되었습니다", response);
    }

     // 게시물 내용 수정
    @PatchMapping("/{postId}")
    public GlobalApiResponse<PostResponse> updatePostContent(@LoginUserResolver User user,
                                                             @PathVariable Long postId,
                                                             @Valid @RequestBody UpdatePostContentRequest request) {

        PostResponse updatePostContentResponse = postService.updatePostContent(user, postId, request);

        return GlobalApiResponse.ok("게시글이 수정되었습니다.", updatePostContentResponse);
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public GlobalApiResponse<?> deletePost(@LoginUserResolver User user,
                                           @PathVariable Long postId) {

        postService.deletePostById(user, postId);

        return GlobalApiResponse.success(HttpStatus.NO_CONTENT, "게시글이 삭제되었습니다.", null);
    }
}
