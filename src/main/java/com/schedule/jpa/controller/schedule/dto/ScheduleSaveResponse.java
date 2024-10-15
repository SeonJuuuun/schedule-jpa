package com.schedule.jpa.controller.schedule.dto;

import com.schedule.jpa.domain.schedule.Schedule;
import com.schedule.jpa.infra.client.weather.dto.WeatherResponse;
import java.time.LocalDateTime;

public record ScheduleSaveResponse(
        String username,
        String title,
        String content,
        LocalDateTime createdAt,
        WeatherResponse response
) {
    public static ScheduleSaveResponse from(final Schedule schedule, final WeatherResponse response) {
        return new ScheduleSaveResponse(
                schedule.getUser().getName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                response
        );
    }
}
