package com.example.newsfeedproject.post.service;

import com.example.newsfeedproject.mapper.PostMapper;
import com.example.newsfeedproject.post.dto.PostRequest;
import com.example.newsfeedproject.post.dto.PostResponse;
import com.example.newsfeedproject.post.dto.UpdatePostContentRequest;
import com.example.newsfeedproject.post.entity.Post;
import com.example.newsfeedproject.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional
    public PostResponse createPost(PostRequest postRequest) {
        Post post = postMapper.toEntity(postRequest);
        postRepository.save(post);
        return postMapper.toResponse(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long postId) {
        Post existingPost = findByIdOrElseThrow(postId);
        return postMapper.toResponse(existingPost);
    }

    @Transactional
    public PostResponse updatePostContent(Long postId, UpdatePostContentRequest updatePostContentRequest) {
        Post existingPost = findByIdOrElseThrow(postId);
        existingPost.updatePostContent(updatePostContentRequest.content());
        postRepository.save(existingPost);
        return postMapper.toResponse(existingPost);
    }

    @Transactional
    public void deletePostById(Long postId) {
        Post existingPost = findByIdOrElseThrow(postId);
        postRepository.delete(existingPost);
    }

    private Post findByIdOrElseThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."));
    }
}
