package com.sparta.todoproject.statusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN_SIGNATURE(400, "유효하지 않은 JWT 서명입니다."),
    INVALID_TOKEN(400, "잘못된 JWT 토큰입니다."),
    EXPIRED_TOKEN(400, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN(400, "지원되지 않는 JWT 토큰입니다."),

    LOGIN_FAILED(400, "로그인에 실패했습니다."),


    PASSWORD_INVALID(401, "비밀번호가 일치하지 않습니다."),
    INVALID_REQUEST_BODY(400, "올바르지 않은 입력이 있습니다."),
    DELETED_SCHEDULE(404, "이미 삭제된 일정입니다."),
    SCHEDULE_NOT_FOUND(404, "해당 일정을 찾을 수 없습니다.");


    private int statusCode;
    private String message;
}
