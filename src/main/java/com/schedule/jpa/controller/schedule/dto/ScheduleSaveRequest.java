package com.schedule.jpa.controller.schedule.dto;

import jakarta.validation.constraints.NotBlank;

public record ScheduleSaveRequest(
        @NotBlank String title,
        @NotBlank String content
) {
}



