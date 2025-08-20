package com.example.newsfeedproject.like.service;

import com.example.newsfeedproject.like.dto.LikeResponse;
import com.example.newsfeedproject.like.dto.ListLikeResponse;
import com.example.newsfeedproject.like.entity.Like;
import com.example.newsfeedproject.like.repository.LikeRepository;
import com.example.newsfeedproject.post.entity.Post;
import com.example.newsfeedproject.post.repository.PostRepository;
import com.example.newsfeedproject.user.dto.PostUserResponse;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.repository.UserRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final int MAX_RETRY = 3;

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TransactionTemplate txTemplate;

    /**
     * 좋아요 기능
     * 낙관적 락 / 충돌 시 MAX_RETRY 값 만큼 반복합니다.
     */
    public LikeResponse addlike(Long userId, Long postId) {

        int attempt = 0;
        while (true) {
            try {
                return txTemplate.execute(status -> {
                    // 게시물 조회
                    Post post = postRepository.findByIdOrElseThrow(postId);

                    if (post.getUser().getId().equals(userId))
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "본인 게시물에 좋아요를 누를 수 없습니다.");

                    // 이미 좋아요 되어 있는 경우
                    if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent())
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요된 게시물입니다.");

                    User user = userRepository.findByIdOrElseThrow(userId);
                    post.increaseLikes();

                    likeRepository.save(new Like(post, user));

                    return new LikeResponse(post.getLikesCount());
                });
            // version이 불일치한 경우 예외 발생
            } catch (ObjectOptimisticLockingFailureException | OptimisticLockException e) {
                // MAX_RETRY까지도 해결되지 않으면 예외를 그대로 controller까지 던짐
                if (++attempt >= MAX_RETRY) throw e;
            }
        }
    }

    /**
     * 좋아요 취소 기능
     * 락 관련 로직은 like 메서드와 동일합니다.
     */
    public LikeResponse deletelike(Long userId, Long postId) {

        int attempt = 0;
        while (true) {
            try {
                return txTemplate.execute(status -> {
                    Post post = postRepository.findByIdOrElseThrow(postId);

                    if (likeRepository.findByUserIdAndPostId(userId, postId).isEmpty())
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요 취소된 게시물입니다.");

                    likeRepository.deleteByUserIdAndPostId(userId, postId);

                    post.decreaseLikes();

                    return new LikeResponse(post.getLikesCount());
                });
            } catch (ObjectOptimisticLockingFailureException | OptimisticLockException e) {
                if (++attempt >= MAX_RETRY) throw e;
            }
        }
    }

    @Transactional(readOnly = true)
    public ListLikeResponse getLikeList(Long postId) {

        Post post = postRepository.findByIdOrElseThrow(postId);

        List<PostUserResponse> likedUsers = likeRepository.findByPostId(postId).stream()
                .map(like -> new PostUserResponse(
                        like.id(),
                        like.name()
                ))
                .toList();

        return new ListLikeResponse(likedUsers, post.getLikesCount());
    }
}
