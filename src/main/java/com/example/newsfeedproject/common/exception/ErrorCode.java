package com.example.newsfeedproject.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("AUTH-001", "사용자를 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS("AUTH-002", "이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST),

    PASSWORD_INCORRECT("USR-001", "비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    PASSWORD_NOT_AVAILABLE("USR-002", "새 비밀번호는 현재 비밀번호와 달라야 합니다.", HttpStatus.BAD_REQUEST),

    POST_NOT_FOUND("POST-001", "존재하지 않는 게시물입니다.", HttpStatus.NOT_FOUND),

    CANNOT_FOLLOW_SELF("FOLL-001", "자기 자신을 팔로우할 수 없습니다.", HttpStatus.BAD_REQUEST),
    ALREADY_FOLLOWED("FOLL-002", "이미 팔로우 한 유저입니다.", HttpStatus.BAD_REQUEST),
    IS_NOT_FOLLOWING("FOLL-003", "팔로우 관계가 아닙니다.", HttpStatus.BAD_REQUEST),

    BOOKMARK_ALREADY_EXISTS("BMK-001", "이미 북마크한 게시물입니다.", HttpStatus.BAD_REQUEST),
    BOOKMARK_NOT_FOUND("BMK-002", "해당 게시물에 대한 북마크가 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    INVALID_INPUT_VALUE("VAL-001", "잘못된 입력값 입니다." , HttpStatus.BAD_REQUEST),

    COMMENT_NOT_FOUND("CMT-001", "댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    FORBIDDEN_COMMENT("CMT-002", "댓글 수정 또는 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
