package com.sparta.todoproject.repository;

import com.sparta.todoproject.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByOrderByCreatedAtDesc();
    Schedule findTopByOrderByIdDesc();
}
