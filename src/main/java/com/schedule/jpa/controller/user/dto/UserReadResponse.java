package com.schedule.jpa.controller.user.dto;

import com.schedule.jpa.domain.user.User;
import java.time.LocalDateTime;

public record UserReadResponse (
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
    public static UserReadResponse from(final User user) {
        return new UserReadResponse(user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
