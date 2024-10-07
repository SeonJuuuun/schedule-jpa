package com.schedule.jpa.controller;

import com.schedule.jpa.controller.dto.ScheduleSaveRequest;
import com.schedule.jpa.controller.dto.ScheduleSaveResponse;
import com.schedule.jpa.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleSaveResponse> create(@RequestBody final ScheduleSaveRequest request) {
        final ScheduleSaveResponse response = scheduleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
