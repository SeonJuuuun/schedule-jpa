package com.schedule.jpa.controller.schedule;

import com.schedule.jpa.common.resolver.AuthenticationUserId;
import com.schedule.jpa.controller.schedule.dto.SchedulePageResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleReadResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleSaveRequest;
import com.schedule.jpa.controller.schedule.dto.ScheduleSaveResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleUpdateRequest;
import com.schedule.jpa.controller.schedule.dto.ScheduleUpdateResponse;
import com.schedule.jpa.service.ScheduleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleSaveResponse> createSchedule(
            @RequestBody @Valid final ScheduleSaveRequest request,
            @AuthenticationUserId final Long userId
    ) {
        final ScheduleSaveResponse response = scheduleService.create(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleReadResponse> readSchedule(@PathVariable final Long scheduleId) {
        final ScheduleReadResponse response = scheduleService.findSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> readSchedules() {
        final List<ScheduleResponse> responses = scheduleService.findSchedules();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/page")
    public ResponseEntity<List<SchedulePageResponse>> readSchedulesPage(
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "10", value = "size") int size
    ) {
        final Page<SchedulePageResponse> schedules = scheduleService.findSchedulesPage(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(schedules.getContent());
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponse> updateSchedule(
            @RequestBody @Valid final ScheduleUpdateRequest request,
            @PathVariable final Long scheduleId,
            @AuthenticationUserId final Long userId
    ) {
        final ScheduleUpdateResponse response = scheduleService.update(request, scheduleId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable final Long scheduleId,
            @AuthenticationUserId final Long userId
    ) {
        scheduleService.delete(scheduleId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
