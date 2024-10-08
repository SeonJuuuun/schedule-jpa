package com.schedule.jpa.service;

import com.schedule.jpa.controller.schedule.dto.ScheduleReadResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleSaveRequest;
import com.schedule.jpa.controller.schedule.dto.ScheduleSaveResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleUpdateRequest;
import com.schedule.jpa.controller.schedule.dto.ScheduleUpdateResponse;
import com.schedule.jpa.domain.schedule.Schedule;
import com.schedule.jpa.domain.schedule.ScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<ScheduleReadResponse> findAll() {
        final List<Schedule> schedules = scheduleRepository.findAll();
        return ScheduleReadResponse.from(schedules);
    }

    @Transactional
    public ScheduleUpdateResponse update(final ScheduleUpdateRequest request, final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        schedule.update(request.title(), request.title());
        return ScheduleUpdateResponse.from(schedule);
    }

    public void delete(final Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}
