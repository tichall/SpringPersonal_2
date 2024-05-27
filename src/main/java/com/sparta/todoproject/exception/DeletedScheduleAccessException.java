package com.sparta.todoproject.exception;

import lombok.Getter;

@Getter
public class DeletedScheduleAccessException extends RuntimeException {
    private ErrorCode errorCode;

    public DeletedScheduleAccessException (String msg, ErrorCode errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}