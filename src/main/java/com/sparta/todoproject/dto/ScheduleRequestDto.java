package com.sparta.todoproject.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String title;
    private String contents;
    private String owner;
    private String password;
}
