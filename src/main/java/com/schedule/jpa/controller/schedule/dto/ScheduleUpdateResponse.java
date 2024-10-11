package com.schedule.jpa.controller.schedule.dto;

import com.schedule.jpa.domain.schedule.Schedule;
import java.time.LocalDateTime;

public record ScheduleUpdateResponse(
        String username,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ScheduleUpdateResponse from(final Schedule schedule) {
        return new ScheduleUpdateResponse(schedule.getUser().getName(), schedule.getTitle(), schedule.getContent(),
                schedule.getCreatedAt(), schedule.getUpdatedAt());
    }
}
