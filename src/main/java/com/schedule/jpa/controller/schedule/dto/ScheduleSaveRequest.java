package com.schedule.jpa.controller.schedule.dto;

public record ScheduleSaveRequest(
        Long userId,
        String title,
        String content
) {
}



