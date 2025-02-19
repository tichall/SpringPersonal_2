package com.sparta.todoproject.repository;

import com.sparta.todoproject.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByOrderByCreatedAtDesc();
    Optional<Schedule> findTopByOrderByIdDesc();
}
