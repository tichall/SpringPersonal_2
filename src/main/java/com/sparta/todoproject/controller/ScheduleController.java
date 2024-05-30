package com.sparta.todoproject.controller;

import com.sparta.todoproject.dto.ScheduleAccessRequestDto;
import com.sparta.todoproject.dto.ScheduleRequestDto;
import com.sparta.todoproject.dto.ScheduleResponseDto;
import com.sparta.todoproject.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping()
    public ScheduleResponseDto createSchedule(@RequestBody @Valid ScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(requestDto);
    }

    @GetMapping()
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleService.getSchedules();
    }

    @GetMapping("/{id}")
    public ScheduleResponseDto getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public String deleteSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleAccessRequestDto requestDto) {
        return scheduleService.deleteSchedule(id, requestDto);
    }

}
