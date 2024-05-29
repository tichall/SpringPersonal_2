package com.sparta.todoproject.repository;

import com.sparta.todoproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByScheduleIdAndId(Long scheduleId, Long id);
}
