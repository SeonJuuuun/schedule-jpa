package com.schedule.jpa.controller.schedule.dto;

import com.schedule.jpa.domain.schedule.Schedule;
import java.time.LocalDateTime;

public record SchedulePageResponse(
        String title,
        String content,
        Integer commentCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String username
) {
    public static SchedulePageResponse from(final Schedule schedule) {
        return new SchedulePageResponse(schedule.getTitle(), schedule.getContent(), schedule.getComments().size(),
                schedule.getCreatedAt(), schedule.getUpdatedAt(), schedule.getUser().getName());
    }
}
