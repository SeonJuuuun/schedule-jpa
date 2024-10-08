package com.schedule.jpa.controller.dto;

public record ScheduleUpdateRequest(
        String title,
        String content
) {
}
