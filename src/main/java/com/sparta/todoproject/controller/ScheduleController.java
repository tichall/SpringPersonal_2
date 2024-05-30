package com.sparta.todoproject.controller;

import com.sparta.todoproject.dto.ResponseMsg;
import com.sparta.todoproject.dto.ScheduleRequestDto;
import com.sparta.todoproject.dto.ScheduleResponseDto;
import com.sparta.todoproject.security.UserDetailsImpl;
import com.sparta.todoproject.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping()
    public ResponseEntity<ResponseMsg<ScheduleResponseDto>> createSchedule(@RequestBody @Valid ScheduleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto, userDetails);

        ResponseMsg<ScheduleResponseDto> responseMsg = ResponseMsg.<ScheduleResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("일정이 추가되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMsg);
    }

    @GetMapping()
    public ResponseEntity<ResponseMsg<List<ScheduleResponseDto>>> getSchedules() {
        List<ScheduleResponseDto> responseDtoList = scheduleService.getSchedules();

        ResponseMsg<List<ScheduleResponseDto>> responseMsg = ResponseMsg.<List<ScheduleResponseDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("모든 일정을 조회합니다.")
                .data(responseDtoList)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMsg);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMsg<ScheduleResponseDto>> getScheduleById(@PathVariable Long id) {
        ScheduleResponseDto responseDto = scheduleService.getScheduleById(id);

        ResponseMsg<ScheduleResponseDto> responseMsg = ResponseMsg.<ScheduleResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("선택한 일정이 조회되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMsg);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMsg<ScheduleResponseDto>> updateSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(id, requestDto, userDetails);

        ResponseMsg<ScheduleResponseDto> responseMsg = ResponseMsg.<ScheduleResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("일정이 수정되었습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMsg);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMsg<Void>> deleteSchedule(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.deleteSchedule(id, userDetails);

        ResponseMsg<Void> responseMsg = ResponseMsg.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("일정이 삭제되었습니다.")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMsg);
    }


}
