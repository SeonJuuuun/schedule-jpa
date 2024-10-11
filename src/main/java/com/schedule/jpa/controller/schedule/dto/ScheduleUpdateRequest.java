package com.schedule.jpa.controller.schedule.dto;

import jakarta.validation.constraints.NotNull;

public record ScheduleUpdateRequest(
        @NotNull
        String title,
        @NotNull
        String content
) {
}
