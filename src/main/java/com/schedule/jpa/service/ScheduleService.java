package com.schedule.jpa.service;

import static com.schedule.jpa.controller.exception.ErrorCodes.SCHEDULE_NOT_FOUND;

import com.schedule.jpa.controller.schedule.dto.SchedulePageResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public ScheduleReadResponse findSchedule(final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleApplicationException(SCHEDULE_NOT_FOUND));
        return ScheduleReadResponse.from(schedule);
    }

    public List<ScheduleResponse> findSchedules() {
        final List<Schedule> schedules = scheduleRepository.findAll();
        return ScheduleResponse.from(schedules);
    }

    public Page<SchedulePageResponse> findSchedulesPage(final int page, final int size) {
        Page<Schedule> schedules = scheduleRepository.findAllByOrderByUpdatedAtDesc(PageRequest.of(page,size));
        return schedules.map(SchedulePageResponse::from);
    }

    @Transactional
    public ScheduleUpdateResponse update(final ScheduleUpdateRequest request, final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleApplicationException(SCHEDULE_NOT_FOUND));
        schedule.update(request.title(), request.title());
        return ScheduleUpdateResponse.from(schedule);
    }

    public void delete(final Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}
