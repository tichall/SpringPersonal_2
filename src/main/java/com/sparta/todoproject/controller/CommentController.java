package com.sparta.todoproject.controller;

import com.sparta.todoproject.dto.CommentAccessRequestDto;
import com.sparta.todoproject.dto.CommentRequestDto;
import com.sparta.todoproject.dto.CommentResponseDto;
import com.sparta.todoproject.dto.ResponseMsg;
import com.sparta.todoproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules/{id}/comments")

public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping
    public ResponseEntity<ResponseMsg<CommentResponseDto>> addComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.addComment(id, requestDto);
    }

    @PutMapping
    public ResponseEntity<ResponseMsg<CommentResponseDto>> updateComment(@PathVariable Long id, @RequestBody CommentAccessRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

//    @DeleteMapping()
//    public ResponseEntity<> deleteComment(@PathVariable Long id, @RequestBody CommentAccessRequestDto requestDto) {
//        return commentService.deleteComment(id, requestDto);
//    }
}
