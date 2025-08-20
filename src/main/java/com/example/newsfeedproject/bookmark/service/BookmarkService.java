package com.example.newsfeedproject.bookmark.service;

import com.example.newsfeedproject.bookmark.entity.Bookmark;
import com.example.newsfeedproject.bookmark.repository.BookmarkRepository;
import com.example.newsfeedproject.common.exception.BusinessException;
import com.example.newsfeedproject.common.exception.ErrorCode;
import com.example.newsfeedproject.mapper.PostMapper;
import com.example.newsfeedproject.post.dto.PostListResponse;
import com.example.newsfeedproject.post.dto.PostResponse;
import com.example.newsfeedproject.post.entity.Post;
import com.example.newsfeedproject.post.repository.PostRepository;
import com.example.newsfeedproject.user.entity.User;
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
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional
    public void addBookmark(User user, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        boolean exists = bookmarkRepository.existsByUserAndPost(user, post);
        if (exists)
            throw new BusinessException(ErrorCode.BOOKMARK_ALREADY_EXISTS);

        Bookmark bookmark = new Bookmark(user, post);

        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void removeBookmark(User user, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        Bookmark bookmark = bookmarkRepository.findByUserAndPost(user, post)
                        .orElseThrow(() -> new BusinessException(ErrorCode.BOOKMARK_NOT_FOUND));

        bookmarkRepository.delete(bookmark);
    }

    @Transactional(readOnly = true)
    public PostListResponse getBookmarks(User user, Pageable pageable) {

        Page<Bookmark> bookmarks = bookmarkRepository.findByUser(user, pageable);
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
