package com.schedule.jpa.controller.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserSaveRequest(
        @NotBlank String name,
        @Email String email
) {
}
