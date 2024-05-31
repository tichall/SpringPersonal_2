package com.sparta.todoproject.service;

import com.sparta.todoproject.dto.*;
import com.sparta.todoproject.entity.Comment;
import com.sparta.todoproject.entity.Schedule;
import com.sparta.todoproject.entity.User;
import com.sparta.todoproject.exception.ObjectNotFoundException;
import com.sparta.todoproject.repository.CommentRepository;
import com.sparta.todoproject.repository.UserRepository;
import com.sparta.todoproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleService scheduleService; // 여기 있는 메서드 쓰려고 이렇게 주입 받아와도 괜찮은가..?
    private final UserRepository userRepository;


    public CommentResponseDto getCommentById(Long scheduleId, Long id) {
        Comment comment = findComment(scheduleId, id);
        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getComments(Long scheduleId) {
        Schedule schedule = scheduleService.findSchedule(scheduleId);

        return commentRepository.findAllByScheduleId(schedule.getId()).stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public CommentResponseDto addComment(Long scheduleId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Schedule schedule = scheduleService.findSchedule(scheduleId);
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("존재하지 않는 사용자입니다.")
        );

        Comment comment = new Comment(requestDto, schedule, user);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long scheduleId, Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = findComment(scheduleId, id);
        checkCommentUser(comment, userDetails);

        comment.update(requestDto);
        commentRepository.flush(); // 먼저 반영

        return new CommentResponseDto(comment);

    }

    @Transactional
    public void deleteComment(Long scheduleId, Long id, UserDetailsImpl userDetails) {

        Comment comment = findComment(scheduleId, id);
        checkCommentUser(comment, userDetails);
        commentRepository.delete(comment);
    }

    private Comment findComment(Long scheduleId, Long id) {
        Schedule schedule = scheduleService.findSchedule(scheduleId);

        return commentRepository.findByScheduleIdAndId(schedule.getId(), id).orElseThrow(() -> new ObjectNotFoundException("선택한 댓글은 존재하지 않습니다."));

    }

    private void checkCommentUser(Comment comment, UserDetailsImpl userDetails) {

        if(!Objects.equals(comment.getUser().getId(), userDetails.getUser().getId())) {
            throw new IllegalArgumentException("댓글 작성자가 아니므로, 접근이 제한됩니다.");
        }
    }
}
