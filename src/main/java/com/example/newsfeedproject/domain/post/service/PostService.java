package com.example.newsfeedproject.domain.post.service;

import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.domain.follow.entity.Follow;
import com.example.newsfeedproject.domain.follow.service.FollowService;
import com.example.newsfeedproject.domain.post.dto.PostListResponse;
import com.example.newsfeedproject.domain.post.dto.PostRequest;
import com.example.newsfeedproject.domain.post.dto.PostResponse;
import com.example.newsfeedproject.domain.post.dto.UpdatePostContentRequest;
import com.example.newsfeedproject.domain.post.entity.Post;
import com.example.newsfeedproject.domain.post.mapper.PostMapper;
import com.example.newsfeedproject.domain.post.repository.PostRepository;
import com.example.newsfeedproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements PostServiceApi {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final FollowService followService;

    @Transactional
    public PostResponse createPost(PostRequest request, User user) {

        Post post = postMapper.toEntity(request, user);

        postRepository.save(post);

        return postMapper.toResponse(post);
    }

    @Transactional(readOnly = true)
    public PostListResponse getPosts(Pageable pageable) {

        Page<Post> postPage = postRepository.findAll(pageable);
        List<PostResponse> postResponses = postMapper.toListResponse(postPage);

        return new PostListResponse(
                postResponses,
                postPage.getNumber(),
                postPage.getTotalPages(),
                postPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long postId) {

        Post existingPost = findPostByIdOrElseThrow(postId);

        return postMapper.toResponse(existingPost);
    }

    @Transactional(readOnly = true)
    public PostListResponse getPostsByPeriod(LocalDateTime start, LocalDateTime end, Pageable pageable) {

        Page<Post> postPage = postRepository.findAllByCreatedAtBetween(start, end, pageable);
        List<PostResponse> postResponses = postMapper.toListResponse(postPage);

        return new PostListResponse(
                postResponses,
                postPage.getNumber(),
                postPage.getTotalPages(),
                postPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PostListResponse getNewsfeed(User user, Pageable pageable) {

        // 내가 팔로우 한 유저들 조회 및 비어있는 리스트 생성
        List<Follow> follows = followService.findAllUserByUser(user);
        List<PostResponse> emptyList = Collections.emptyList();

        // 팔로우하는 사람이 없을 경우 비어있는 리스트 반환
        if (follows.isEmpty())
            return new PostListResponse(emptyList, pageable.getPageNumber(), 0, 0L);

        // 팔로우하는 사람 목록 추출
        List<User> followingUsers = follows.stream()
                .map(Follow::getFollowingUser)
                .toList();

        // 팔로우하는 사람들의 게시물 조회
        Page<Post> postPage = postRepository.findByUserIn(followingUsers, pageable);

        // 조회된 게시물 DTO 변환
        List<PostResponse> postResponses = postMapper.toListResponse(postPage);

        return new PostListResponse(
                postResponses,
                postPage.getNumber(),
                postPage.getTotalPages(),
                postPage.getTotalElements());
    }

    @Transactional
    public PostResponse updatePostContent(User user, Long postId, UpdatePostContentRequest request) {

        Post existingPost = findPostByIdOrElseThrow(postId);
        validatePostAuthor(user, existingPost);

        existingPost.updatePostContent(request.content());

        return postMapper.toResponse(existingPost);
    }

    @Transactional
    public void deletePostById(User user, Long postId) {

        Post existingPost = findPostByIdOrElseThrow(postId);
        validatePostAuthor(user, existingPost);

        postRepository.delete(existingPost);
    }

    private void validatePostAuthor(User user, Post post) {

        if (!post.getUser().getId().equals(user.getId())) {
            throw new BusinessException(ErrorCode.FORBIDDEN_POST);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Post findPostByIdOrElseThrow(Long id) {

        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }
}
