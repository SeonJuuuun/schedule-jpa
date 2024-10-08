package com.schedule.jpa.controller.schedule.dto;

public record ScheduleUpdateRequest(
        String title,
        String content
) {
}
