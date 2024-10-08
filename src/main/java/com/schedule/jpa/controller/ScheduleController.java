package com.schedule.jpa.controller;

import com.schedule.jpa.controller.dto.ScheduleReadResponse;
import com.schedule.jpa.controller.dto.ScheduleSaveRequest;
import com.schedule.jpa.controller.dto.ScheduleSaveResponse;
import com.schedule.jpa.controller.dto.ScheduleUpdateRequest;
import com.schedule.jpa.controller.dto.ScheduleUpdateResponse;
import com.schedule.jpa.service.ScheduleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleSaveResponse> createSchedule(@RequestBody final ScheduleSaveRequest request) {
        final ScheduleSaveResponse response = scheduleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleReadResponse> readSchedule(@PathVariable final Long scheduleId) {
        final ScheduleReadResponse response = scheduleService.findById(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleReadResponse>> readSchedules() {
        final List<ScheduleReadResponse> responses = scheduleService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponse> updateSchedule(
            @RequestBody final ScheduleUpdateRequest request,
            @PathVariable final Long scheduleId
    ) {
        final ScheduleUpdateResponse response = scheduleService.update(request, scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
