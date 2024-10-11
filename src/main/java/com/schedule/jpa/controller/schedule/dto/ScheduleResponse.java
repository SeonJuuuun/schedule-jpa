package com.schedule.jpa.controller.schedule.dto;

import com.schedule.jpa.domain.schedule.Schedule;
import java.time.LocalDateTime;
import java.util.List;

public record ScheduleResponse(
        String username,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ScheduleResponse from(final Schedule schedule) {
        return new ScheduleResponse(schedule.getUser().getName(), schedule.getTitle(), schedule.getContent(),
                schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    public static List<ScheduleResponse> from(final List<Schedule> schedules) {
        return schedules.stream()
                .map(ScheduleResponse::from)
                .toList();
    }
}
