package com.sparta.todoproject.service;

import com.sparta.todoproject.dto.CommentAccessRequestDto;
import com.sparta.todoproject.dto.CommentRequestDto;
import com.sparta.todoproject.dto.CommentResponseDto;
import com.sparta.todoproject.entity.Comment;
import com.sparta.todoproject.entity.Schedule;
import com.sparta.todoproject.exception.ObjectNotFoundException;
import com.sparta.todoproject.repository.CommentRepository;
import com.sparta.todoproject.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.TaskSchedulerRouter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
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
        checkContentsValid(requestDto);
        Comment comment = new Comment(requestDto, scheduleService.findSchedule(id));
        commentRepository.save(comment);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CommentResponseDto(comment));
    }

    @Transactional
    public ResponseEntity<CommentResponseDto> updateComment(Long id, CommentAccessRequestDto requestDto) {
        checkContentsValid(requestDto);
        Schedule schedule = scheduleService.findSchedule(id);
        Comment comment = checkCommentValid(schedule, requestDto);

        comment.update(requestDto);
        commentRepository.flush(); // 먼저 반영
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CommentResponseDto(comment));
    }

    private void checkContentsValid(CommentRequestDto requestDto) {
        Optional.ofNullable(requestDto.getContents()).orElseThrow(() -> new IllegalArgumentException("댓글 내용이 비어있습니다."));
        if (requestDto.getContents().isBlank()) {
            throw new IllegalArgumentException("댓글 내용이 비어있습니다.");
        }
    }

    private Comment checkCommentValid(Schedule schedule, CommentAccessRequestDto requestDto) {
        // 해당 스케줄에 있는 댓글 중 아이디가 일치하는 것을 찾아야 함...
        Comment comment = commentRepository.findByScheduleIdAndId(schedule.getId(), requestDto.getId()).orElseThrow(() -> new ObjectNotFoundException("선택한 댓글은 존재하지 않습니다."));

        if(!Objects.equals(comment.getUserId(), requestDto.getUserId())) {
            throw new IllegalArgumentException("유저 아이디가 일치하지 않습니다.");
        }
        return comment;
    }
}
