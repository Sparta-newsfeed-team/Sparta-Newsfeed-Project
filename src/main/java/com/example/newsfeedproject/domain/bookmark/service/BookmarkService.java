package com.example.newsfeedproject.domain.bookmark.service;

import com.example.newsfeedproject.domain.bookmark.entity.Bookmark;
import com.example.newsfeedproject.domain.bookmark.repository.BookmarkRepository;
import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.domain.post.mapper.PostMapper;
import com.example.newsfeedproject.domain.post.dto.PostListResponse;
import com.example.newsfeedproject.domain.post.dto.PostResponse;
import com.example.newsfeedproject.domain.post.entity.Post;
import com.example.newsfeedproject.domain.post.service.PostServiceApi;
import com.example.newsfeedproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostMapper postMapper;
    private final PostServiceApi postService;

    @Transactional
    public void addBookmark(User user, Long postId) {

        boolean exists = bookmarkRepository.existsByUserIdAndPostId(user.getId(), postId);
        if (exists)
            throw new BusinessException(ErrorCode.BOOKMARK_ALREADY_EXISTS);

        Post post = postService.findPostByIdOrElseThrow(postId);
        Bookmark bookmark = new Bookmark(user, post);

        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void removeBookmark(User user, Long postId) {

        boolean exists = bookmarkRepository.existsByUserIdAndPostId(user.getId(), postId);
        if (!exists)
            throw new BusinessException(ErrorCode.BOOKMARK_NOT_FOUND);

        bookmarkRepository.deleteByUserIdAndPostId(user.getId(), postId);
    }

    @Transactional(readOnly = true)
    public PostListResponse getBookmarks(User user, Pageable pageable) {

        Page<Bookmark> bookmarks = bookmarkRepository.findByUserId(user.getId(), pageable);
        Page<Post> postPage = bookmarks.map(Bookmark::getPost);
        List<PostResponse> postResponses = postMapper.toListResponse(postPage);

        return new PostListResponse(
                postResponses,
                postPage.getNumber(),
                postPage.getTotalPages(),
                postPage.getTotalElements()
        );
    }
}
