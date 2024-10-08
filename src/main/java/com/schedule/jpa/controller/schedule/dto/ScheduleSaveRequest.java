package com.schedule.jpa.controller.schedule.dto;

public record ScheduleSaveRequest(
        String username,
        String title,
        String content
) {
}



