package com.sparta.todoproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleAccessRequestDto {
    @NotBlank
    private String password;
}
