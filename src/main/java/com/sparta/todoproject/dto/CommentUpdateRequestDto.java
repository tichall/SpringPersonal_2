package com.sparta.todoproject.dto;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto extends CommentAccessRequestDto{
    private String contents;
}
