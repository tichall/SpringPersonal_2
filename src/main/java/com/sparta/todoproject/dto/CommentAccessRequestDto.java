package com.sparta.todoproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 삭제 요청 DTO
 */
@Getter
public class CommentAccessRequestDto {
    @NotBlank
    private String userId;
}
