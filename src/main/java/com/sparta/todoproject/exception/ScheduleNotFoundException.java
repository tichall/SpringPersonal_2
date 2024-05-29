package com.sparta.todoproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ScheduleNotFoundException extends RuntimeException {

    public ScheduleNotFoundException (String msg) {
        super(msg);
    }
}