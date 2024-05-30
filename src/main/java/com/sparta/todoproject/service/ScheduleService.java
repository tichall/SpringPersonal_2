package com.sparta.todoproject.service;

import com.sparta.todoproject.dto.ScheduleAccessRequestDto;
import com.sparta.todoproject.dto.ScheduleRequestDto;
import com.sparta.todoproject.dto.ScheduleResponseDto;
import com.sparta.todoproject.entity.Schedule;
import com.sparta.todoproject.exception.DeletedScheduleAccessException;
import com.sparta.todoproject.exception.PasswordInvalidException;
import com.sparta.todoproject.exception.ObjectNotFoundException;
import com.sparta.todoproject.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ScheduleService {
    private static final String DELETE_SUCCESS = "삭제 성공!";

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        Schedule saveSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(saveSchedule);
    }

    public List<ScheduleResponseDto> getSchedules() {
        return scheduleRepository.findAllByOrderByCreatedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = findSchedule(id);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = findSchedule(id);
        checkPassword(requestDto.getPassword(), schedule);
        schedule.update(requestDto);
        scheduleRepository.flush();
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, ScheduleAccessRequestDto requestDto) {
        Schedule schedule = findSchedule(id);
        checkPassword(requestDto.getPassword(), schedule);
        scheduleRepository.delete(schedule);
    }

    public Schedule findSchedule(Long id) {
        // 가장 최근에 생성된 일정 조회
        Schedule schedule = scheduleRepository.findTopByOrderByIdDesc().orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다."));

        return scheduleRepository.findById(id).orElseThrow(() -> {
            if (id < schedule.getId()) {
                throw new DeletedScheduleAccessException("삭제된 일정 접근");
            }
            throw new ObjectNotFoundException("선택한 일정은 존재하지 않습니다!");
        });
    }

    /**
     * 비밀번호 일치 여부 확인
     * @param InputPassword 입력한 비밀번호
     * @param schedule 비밀번호 비교할 일정 객체
     * @return 비밀번호 일치 -> true, 불일치 -> false
     *
     */
    private void checkPassword(String InputPassword, Schedule schedule) {
        if (!Objects.equals(InputPassword, schedule.getPassword())) {
            throw new PasswordInvalidException("비밀번호 오류");
        }
    }
}
