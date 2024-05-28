package com.sparta.todoproject.exception;

import lombok.Getter;

@Getter
public class ScheduleNotFoundException extends RuntimeException {
    private ErrorCode errorCode;

    public ScheduleNotFoundException (String msg, ErrorCode errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}