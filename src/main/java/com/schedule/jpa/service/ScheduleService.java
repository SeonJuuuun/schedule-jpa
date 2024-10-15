package com.schedule.jpa.service;

import static com.schedule.jpa.controller.exception.ErrorCodes.SCHEDULE_NOT_FOUND;
import static com.schedule.jpa.controller.exception.ErrorCodes.SCHEDULE_VERIFY_OWNER;
import static com.schedule.jpa.controller.exception.ErrorCodes.USER_NOT_ADMIN;
import static com.schedule.jpa.controller.exception.ErrorCodes.USER_NOT_FOUND;

import com.schedule.jpa.controller.schedule.dto.SchedulePageResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleReadResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleSaveRequest;
import com.schedule.jpa.controller.schedule.dto.ScheduleSaveResponse;
import com.schedule.jpa.controller.schedule.dto.ScheduleUpdateRequest;
import com.schedule.jpa.controller.schedule.dto.ScheduleUpdateResponse;
import com.schedule.jpa.domain.schedule.Schedule;
import com.schedule.jpa.domain.user.User;
import com.schedule.jpa.domain.weather.Weather;
import com.schedule.jpa.infra.client.weather.WeatherClient;
import com.schedule.jpa.infra.client.weather.dto.WeatherResponse;
import com.schedule.jpa.infra.repository.ScheduleRepository;
import com.schedule.jpa.infra.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final UserRepository userRepository;
    private final WeatherClient weatherClient;

    @Transactional
    public ScheduleSaveResponse create(final ScheduleSaveRequest request, final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ScheduleApplicationException(USER_NOT_FOUND));
        final WeatherResponse weatherResponse = weatherClient.getWeather(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd")));
        final Weather weather = Weather.of(weatherResponse.date(), weatherResponse.weather());
        final Schedule schedule = Schedule.of(user, request.title(), weather, request.content());
        final Schedule savedSchedule = scheduleRepository.save(schedule);
        user.addWriteSchedules(schedule);
        return ScheduleSaveResponse.from(savedSchedule, weatherResponse);
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
        Page<Schedule> schedules = scheduleRepository.findAllByOrderByUpdatedAtDesc(PageRequest.of(page, size));
        return schedules.map(SchedulePageResponse::from);
    }

    @Transactional
    public ScheduleUpdateResponse update(
            final ScheduleUpdateRequest request,
            final Long scheduleId,
            final Long userId
    ) {
        final Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleApplicationException(SCHEDULE_NOT_FOUND));
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ScheduleApplicationException(USER_NOT_FOUND));

        if (!schedule.verifyOwner(user)) {
            throw new ScheduleApplicationException(SCHEDULE_VERIFY_OWNER);
        }

        if (!user.isAdmin()) {
            throw new ScheduleApplicationException(USER_NOT_ADMIN);
        }
        schedule.update(request.title(), request.content());
        return ScheduleUpdateResponse.from(schedule);
    }

    public void delete(final Long scheduleId, final Long userId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleApplicationException(SCHEDULE_NOT_FOUND));

        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ScheduleApplicationException(USER_NOT_FOUND));

        if (!schedule.verifyOwner(user)) {
            throw new ScheduleApplicationException(SCHEDULE_VERIFY_OWNER);
        }

        if (!user.isAdmin()) {
            throw new ScheduleApplicationException(USER_NOT_ADMIN);
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
