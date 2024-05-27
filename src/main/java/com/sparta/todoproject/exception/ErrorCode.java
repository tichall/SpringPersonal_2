package com.sparta.todoproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    PASSWORD_INVALID(401, "비밀번호가 일치하지 않습니다."),
    DELETED_SCHEDULE(404, "이미 삭제된 일정입니다."),
    SCHEDULE_NOT_FOUND(404, "해당 일정을 찾을 수 없습니다.");

    private int status;
    private String msg;
}
