package com.example.newsfeedproject.domain.bookmark.controller;

import com.example.newsfeedproject.common.dto.GlobalApiResponse;
import com.example.newsfeedproject.domain.bookmark.service.BookmarkService;
import com.example.newsfeedproject.common.annotation.LoginUserResolver;
import com.example.newsfeedproject.domain.post.dto.PostListResponse;
import com.example.newsfeedproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/posts/{postId}/bookmarks")
    public GlobalApiResponse<?> addBookmark(@LoginUserResolver User user,
                                         @PathVariable Long postId) {

        bookmarkService.addBookmark(user, postId);

        return GlobalApiResponse.ok("북마크가 추가되었습니다.", null);
    }

    @DeleteMapping("/posts/{postId}/bookmarks")
    public GlobalApiResponse<?> removeBookmark(@LoginUserResolver User user,
                                                 @PathVariable Long postId) {

        bookmarkService.removeBookmark(user, postId);

        return GlobalApiResponse.ok("북마크가 삭제되었습니다.", null);
    }

    @GetMapping("/bookmarks")
    public GlobalApiResponse<PostListResponse> getBookmarks(@LoginUserResolver User user,
                                                            @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        PostListResponse postListResponse = bookmarkService.getBookmarks(user, pageable);

        return GlobalApiResponse.ok("북마크된 게시물이 조회되었습니다.", postListResponse);
    }
}
