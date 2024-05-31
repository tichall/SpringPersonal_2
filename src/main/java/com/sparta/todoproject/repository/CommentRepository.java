package com.sparta.todoproject.repository;

import com.sparta.todoproject.dto.CommentResponseDto;
import com.sparta.todoproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByScheduleIdAndId(Long scheduleId, Long id);

    List<Comment> findAllByScheduleId(Long scheduleId);
}
