package com.schedule.jpa.domain.user;

import static com.schedule.jpa.controller.exception.ErrorCodes.ROLE_NOT_FOUND;

import com.schedule.jpa.service.ScheduleApplicationException;
import java.util.Arrays;

public enum Role {
    GENERAL,
    ADMIN;

    public static Role from(final String role) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new ScheduleApplicationException(ROLE_NOT_FOUND));
    }
}
