package com.example.newsfeedproject.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("AUTH-001", "사용자를 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS("AUTH-002", "이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_USER("AUTH-003", "사용할 수 없는 사용자입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("AUTH-004", "유효하지 않은 리프레시 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_ACCESS_TOKEN("AUTH-005", "유효하지 않은 액세스 토큰입니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("AUTH-006", "만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),

    PASSWORD_INCORRECT("USR-001", "비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    PASSWORD_NOT_AVAILABLE("USR-002", "새 비밀번호는 현재 비밀번호와 달라야 합니다.", HttpStatus.BAD_REQUEST),

    POST_NOT_FOUND("POST-001", "존재하지 않는 게시물입니다.", HttpStatus.NOT_FOUND),
    FORBIDDEN_POST("POST-002", "작성자만 수정 또는 삭제할 수 있습니다.", HttpStatus.FORBIDDEN),

    CANNOT_FOLLOW_SELF("FOLL-001", "자기 자신을 팔로우할 수 없습니다.", HttpStatus.BAD_REQUEST),
    ALREADY_FOLLOWED("FOLL-002", "이미 팔로우 한 유저입니다.", HttpStatus.BAD_REQUEST),
    IS_NOT_FOLLOWING("FOLL-003", "팔로우 관계가 아닙니다.", HttpStatus.BAD_REQUEST),

    BOOKMARK_ALREADY_EXISTS("BMK-001", "이미 북마크한 게시물입니다.", HttpStatus.BAD_REQUEST),
    BOOKMARK_NOT_FOUND("BMK-002", "해당 게시물에 대한 북마크가 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    INVALID_INPUT_VALUE("VAL-001", "잘못된 입력값 입니다." , HttpStatus.BAD_REQUEST),

    COMMENT_NOT_FOUND("CMT-001", "댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    FORBIDDEN_COMMENT("CMT-002", "댓글 수정 또는 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN),

    HASHTAG_NOT_FOUND("TAG-001", "존재하지 않는 해시태그입니다.", HttpStatus.NOT_FOUND),

    CANNOT_LIKE_SELF("LIKE-001", "본인 게시물에 좋아요를 누를 수 없습니다.", HttpStatus.CONFLICT),
    ALREADY_LIKE_APPLIED("LIKE-002", "이미 좋아요된 게시물입니다.", HttpStatus.BAD_REQUEST),
    LIKE_NOT_APPLIED("LIKE-003", "좋아요가 되어있지 않은 게시물입니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
