package com.schedule.jpa.controller.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ScheduleSaveRequest(
        @NotNull Long userId,
        @NotBlank String title,
        @NotBlank String content
) {
}



