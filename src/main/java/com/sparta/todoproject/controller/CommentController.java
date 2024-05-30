package com.sparta.todoproject.controller;

import com.sparta.todoproject.dto.*;
import com.sparta.todoproject.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules/{scheduleId}/comments")

public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 댓글 추가
     * @param id
     * @param requestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseMsg<CommentResponseDto>> addComment(@PathVariable Long scheduleId, @RequestBody @Valid CommentRequestDto requestDto) {
        return commentService.addComment(scheduleId, requestDto);
    }

    /**
     * 댓글 수정
     * @param id
     * @param requestDto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMsg<CommentResponseDto>> updateComment(@PathVariable Long scheduleId, @PathVariable Long id, @RequestBody @Valid CommentRequestDto requestDto) {
        return commentService.updateComment(scheduleId, id, requestDto);
    }

    /**
     * 댓글 삭제
     * @param id
     * @param requestDto
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMsg<Void>> deleteComment(@PathVariable Long scheduleId, @PathVariable Long id, @RequestBody @Valid CommentAccessRequestDto requestDto) {
        return commentService.deleteComment(scheduleId, id, requestDto);
    }
}
