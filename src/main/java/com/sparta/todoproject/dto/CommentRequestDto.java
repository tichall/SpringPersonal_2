package com.sparta.todoproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto extends CommentAccessRequestDto {
    @NotBlank
    private String contents;
}
