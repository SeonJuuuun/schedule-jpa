package com.schedule.jpa.controller.user.dto;

public record UserUpdateRequest(
        String name,
        String email
) {
}
