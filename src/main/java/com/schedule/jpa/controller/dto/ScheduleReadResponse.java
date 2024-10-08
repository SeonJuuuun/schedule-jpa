package com.schedule.jpa.controller.dto;

import com.schedule.jpa.domain.schedule.Schedule;
import java.time.LocalDateTime;
import java.util.List;

public record ScheduleReadResponse(
        String username,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ScheduleReadResponse from(final Schedule schedule) {
        return new ScheduleReadResponse(schedule.getUsername(), schedule.getTitle(), schedule.getContent(),
                schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    public static List<ScheduleReadResponse> from(final List<Schedule> schedules) {
        return schedules.stream()
                .map(ScheduleReadResponse::from)
                .toList();
    }
}