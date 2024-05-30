package com.sparta.todoproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    @NotBlank
    private String owner;

    @NotBlank
    private String password;
}
