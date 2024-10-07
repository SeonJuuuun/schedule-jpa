package com.schedule.jpa.service;

import com.schedule.jpa.controller.dto.ScheduleReadResponse;
import com.schedule.jpa.controller.dto.ScheduleSaveRequest;
import com.schedule.jpa.controller.dto.ScheduleSaveResponse;
import com.schedule.jpa.domain.schedule.Schedule;
import com.schedule.jpa.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleSaveResponse create(final ScheduleSaveRequest request) {
        final Schedule schedule = Schedule.of(request.username(), request.title(), request.content());
        final Schedule savedSchedule = scheduleRepository.save(schedule);
        return ScheduleSaveResponse.from(savedSchedule);
    }

    public ScheduleReadResponse findById(final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        return ScheduleReadResponse.from(schedule);
    }
}
