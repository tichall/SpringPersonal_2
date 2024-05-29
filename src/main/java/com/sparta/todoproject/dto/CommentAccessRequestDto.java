package com.sparta.todoproject.dto;

import lombok.Getter;

/**
 * 삭제 요청 DTO
 */
@Getter
public class CommentAccessRequestDto {
    private Long id;
    private String userId;
}
