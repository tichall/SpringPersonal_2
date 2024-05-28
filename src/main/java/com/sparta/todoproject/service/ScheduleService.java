package com.sparta.todoproject.service;

import com.sparta.todoproject.dto.ScheduleDeleteRequestDto;
import com.sparta.todoproject.dto.ScheduleRequestDto;
import com.sparta.todoproject.dto.ScheduleResponseDto;
import com.sparta.todoproject.entity.Schedule;
import com.sparta.todoproject.exception.DeletedScheduleAccessException;
import com.sparta.todoproject.exception.PasswordInvalidException;
import com.sparta.todoproject.exception.ScheduleNotFoundException;
import com.sparta.todoproject.exception.ErrorCode;
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
    public String deleteSchedule(Long id, ScheduleDeleteRequestDto requestDto) {
        Schedule schedule = findSchedule(id);
        checkPassword(requestDto.getPassword(), schedule);
        scheduleRepository.delete(schedule);
        return DELETE_SUCCESS; // String이 아니라 ResponseEntity 반환으로 바꿀 수는 없을까..?
    }

    public Schedule findSchedule(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);

        return scheduleRepository.findById(id).orElseThrow(() -> {
            if (id < scheduleRepository.findTopByOrderByIdDesc().getId()) {
                throw new DeletedScheduleAccessException("삭제된 일정 접근", ErrorCode.DELETED_SCHEDULE);
            }
            throw new ScheduleNotFoundException("선택한 일정은 존재하지 않습니다!", ErrorCode.SCHEDULE_NOT_FOUND);
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
            throw new PasswordInvalidException("비밀번호 오류", ErrorCode.PASSWORD_INVALID);
        }
    }
}
