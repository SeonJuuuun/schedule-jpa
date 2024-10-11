package com.schedule.jpa.controller.user.dto;

import com.schedule.jpa.domain.jwt.Jwt;
import com.schedule.jpa.domain.user.User;
import java.time.LocalDateTime;

public record UserSaveResponse(
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String accessToken
) {
    public static UserSaveResponse from(final User user, Jwt accessToken) {
        return new UserSaveResponse(user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt(),
                accessToken.token());
    }
}
