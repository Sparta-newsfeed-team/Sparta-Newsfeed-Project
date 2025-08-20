package com.example.newsfeedproject.bookmark.controller;

import com.example.newsfeedproject.bookmark.service.BookmarkService;
import com.example.newsfeedproject.common.annoation.LoginUserResolver;
import com.example.newsfeedproject.post.dto.PostListResponse;
import com.example.newsfeedproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/posts/{postId}/bookmarks")
    public ResponseEntity<String> addBookmark(@LoginUserResolver User user,
                                              @PathVariable Long postId) {

        bookmarkService.addBookmark(user, postId);

        return ResponseEntity.ok().body("북마크가 추가되었습니다.");
    }

    @DeleteMapping("/posts/{postId}/bookmarks")
    public ResponseEntity<String> removeBookmark(@LoginUserResolver User user,
                                                 @PathVariable Long postId) {

        bookmarkService.removeBookmark(user, postId);

        return ResponseEntity.ok().body("북마크가 삭제되었습니다.");
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<PostListResponse> getBookmarks(@LoginUserResolver User user,
                                                         @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        PostListResponse postListResponse = bookmarkService.getBookmarks(user, pageable);

        return ResponseEntity.ok(postListResponse);
    }
}
