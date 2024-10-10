package com.schedule.jpa.service;

import com.schedule.jpa.controller.comment.dto.CommentReadResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleReadResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleSaveRequest;
import com.schedule.jpa.controller.schedule.dto.ScheduleSaveResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleUpdateRequest;
import com.schedule.jpa.controller.schedule.dto.ScheduleUpdateResponse;
import com.schedule.jpa.domain.schedule.Schedule;
import com.schedule.jpa.domain.schedule.ScheduleRepository;
import java.util.List;
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

    public ScheduleReadResponse findSchedule(final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        return ScheduleReadResponse.from(schedule);
    }

    public List<ScheduleResponse> findSchedules() {
        final List<Schedule> schedules = scheduleRepository.findAll();
        return ScheduleResponse.from(schedules);
    }

    public ScheduleUpdateResponse update(final ScheduleUpdateRequest request, final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        schedule.update(request.title(), request.title());
        scheduleRepository.save(schedule);
        return ScheduleUpdateResponse.from(schedule);
    }

    public void delete(final Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}
