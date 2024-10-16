package com.schedule.jpa.controller.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;

public enum ErrorCodes {

    // 일정 관련
    SCHEDULE_NOT_FOUND("일정이 존재하지 않습니다.", 2001L, NOT_FOUND),
    SCHEDULE_VERIFY_OWNER("본인의 일정이 아닙니다.", 2002L, FORBIDDEN),

    // 댓글 관련
    COMMENT_NOT_FOUND("댓글이 존재하지 않습니다.", 3001L, NOT_FOUND),
    COMMENT_NOT_FOUND_IN_SCHEDULE("댓글이 해당 스케줄에 속해 있지 않습니다.", 3002L, NOT_FOUND),

    // 유저 관련
    USER_NOT_FOUND("유저가 존재하지 않습니다.", 4001L, NOT_FOUND),
    INVALID_EMAIL("이메일이 존재하지 않습니다.", 4002L, UNAUTHORIZED),
    INVALID_PASSWORD("패스워드가 올바르지 않습니다.", 4003L, UNAUTHORIZED),
    USER_NOT_ADMIN("유저의 역할이 관리자가 아닙니다.", 4004L, FORBIDDEN),
    USER_VERIFY_OWNER("유저 본인이 아닙니다.", 4005L, FORBIDDEN),

    // 토큰 관련
    TOKEN_EXPIRED("토큰의 유효기간이 만료되었습니다.", 5001L, UNAUTHORIZED),
    TOKEN_NULL_EXCEPTION("토큰이 존재하지 않습니다.", 5002L, HttpStatus.BAD_REQUEST),

    // 역할 관련
    ROLE_NOT_FOUND("잘못된 사용자 입니다.", 6001L, HttpStatus.BAD_REQUEST),

    // 날씨 관련
    WEATHER_NOT_FOUND("날씨 정보가 없습니다.", 7001L, NOT_FOUND),

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
