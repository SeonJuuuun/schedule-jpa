package com.schedule.jpa.controller.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

public enum ErrorCodes {

    // 일정 관련
    SCHEDULE_NOT_FOUND("일정이 존재하지 않습니다.", 2001L, NOT_FOUND),

    // 댓글 관련
    COMMENT_NOT_FOUND("댓글이 존재하지 않습니다.", 3001L, NOT_FOUND),
    COMMENT_NOT_FOUND_IN_SCHEDULE("댓글이 해당 스케줄에 속해 있지 않습니다.", 3002L, NOT_FOUND),

    // 유저 관련
    USER_NOT_FOUND("유저가 존재하지 않습니다.", 4001L, NOT_FOUND),


    BAD_REQUEST("BAD_REQUEST", 9404L, HttpStatus.BAD_REQUEST),
    BAD_REQUEST_JSON_PARSE_ERROR("[BAD_REQUEST] JSON_PARSE_ERROR - 올바른 JSON 형식이 아님", 9405L, HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", 9999L, HttpStatus.INTERNAL_SERVER_ERROR);

    public final String message;
    public final Long code;
    public final HttpStatus status;

    ErrorCodes(final String message, final Long code, final HttpStatus status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }
}
