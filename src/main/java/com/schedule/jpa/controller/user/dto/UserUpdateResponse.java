package com.schedule.jpa.controller.user.dto;

import com.schedule.jpa.domain.user.User;

public record UserUpdateResponse(
        String name,
        String email
) {
    public static UserUpdateResponse from(final User user) {
        return new UserUpdateResponse(user.getName(), user.getEmail());
    }
}
