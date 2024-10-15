package com.schedule.jpa.infra.client.weather.dto;

public record WeatherResponse(
        String date,
        String weather
) {
}
