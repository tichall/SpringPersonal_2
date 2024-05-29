package com.sparta.todoproject.service;

import com.sparta.todoproject.dto.CommentRequestDto;
import com.sparta.todoproject.dto.CommentResponseDto;
import com.sparta.todoproject.entity.Comment;
import com.sparta.todoproject.entity.Schedule;
import com.sparta.todoproject.repository.CommentRepository;
import com.sparta.todoproject.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.TaskSchedulerRouter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService; // 여기 있는 메서드 쓰려고 이렇게 주입 받아와도 괜찮은가..?

    public CommentService(CommentRepository commentRepository, ScheduleRepository scheduleRepository, ScheduleService scheduleService) {
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
    }

    public ResponseEntity<CommentResponseDto> addComment(Long id, CommentRequestDto requestDto) {
        Optional.ofNullable(requestDto.getContents()).orElseThrow(() -> new IllegalArgumentException("댓글 내용이 비어있습니다."));

        Comment comment = new Comment(requestDto, scheduleService.findSchedule(id));
        commentRepository.save(comment);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CommentResponseDto(comment));
    }




}
