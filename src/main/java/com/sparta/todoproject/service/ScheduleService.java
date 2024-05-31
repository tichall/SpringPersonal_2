package com.sparta.todoproject.service;

import com.sparta.todoproject.dto.ScheduleRequestDto;
import com.sparta.todoproject.dto.ScheduleResponseDto;
import com.sparta.todoproject.entity.Schedule;
import com.sparta.todoproject.exception.ObjectNotFoundException;
import com.sparta.todoproject.repository.ScheduleRepository;
import com.sparta.todoproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto, UserDetailsImpl userDetails) {
        Schedule schedule = new Schedule(requestDto, userDetails.getUser());
        Schedule saveSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(saveSchedule);
    }

    // 여기서 일정 하나도 없으면..?
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleRepository.findAllByOrderByCreatedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = findSchedule(id);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto, UserDetailsImpl userDetails) {
        Schedule schedule = findSchedule(id);
        checkScheduleUser(schedule, userDetails);

        schedule.update(requestDto);
        scheduleRepository.flush();
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, UserDetailsImpl userDetails) {
        Schedule schedule = findSchedule(id);
        checkScheduleUser(schedule, userDetails);

        scheduleRepository.delete(schedule);
    }

    public Schedule findSchedule(Long id) {
        // 가장 최근에 생성된 일정 조회
        Schedule schedule = scheduleRepository.findTopByOrderByIdDesc().orElseThrow(() -> new ObjectNotFoundException("일정이 존재하지 않습니다."));

        return scheduleRepository.findById(id).orElseThrow(() -> {
            if (id < schedule.getId()) {
                throw new ObjectNotFoundException("삭제된 일정 접근");
            }
            throw new ObjectNotFoundException("선택한 일정은 존재하지 않습니다!");
        });
    }

    public void checkScheduleUser(Schedule schedule, UserDetailsImpl userDetails) {
        if (!Objects.equals(schedule.getUser().getId(), userDetails.getUser().getId())) {
            throw new IllegalArgumentException("일정 작성자가 아니므로, 접근이 제한됩니다.");
        }
    }
}
