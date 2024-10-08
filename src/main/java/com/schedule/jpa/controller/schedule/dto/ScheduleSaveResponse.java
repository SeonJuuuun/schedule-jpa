package com.schedule.jpa.controller.schedule.dto;

import com.schedule.jpa.domain.schedule.Schedule;
import java.time.LocalDateTime;

public record ScheduleSaveResponse(
        String username,
        String title,
        String content,
        LocalDateTime createdAt
) {
    public static ScheduleSaveResponse from(final Schedule schedule) {
        return new ScheduleSaveResponse(
                schedule.getUsername(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt()
        );
    }
}
