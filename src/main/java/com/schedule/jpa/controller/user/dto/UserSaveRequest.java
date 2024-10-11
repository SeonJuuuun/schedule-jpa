package com.schedule.jpa.controller.user.dto;

public record UserSaveRequest(
        String name,
        String email
) {
}
