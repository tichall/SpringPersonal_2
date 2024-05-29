package com.sparta.todoproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DeletedScheduleAccessException extends RuntimeException {

    public DeletedScheduleAccessException (String msg) {
        super(msg);
    }
}