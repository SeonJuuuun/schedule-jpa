package com.schedule.jpa.controller.dto;

public record ScheduleSaveRequest(
        String username,
        String title,
        String content
) {
}



