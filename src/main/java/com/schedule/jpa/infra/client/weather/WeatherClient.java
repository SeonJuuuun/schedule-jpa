package com.schedule.jpa.infra.client.weather;

import static com.schedule.jpa.controller.exception.ErrorCodes.WEATHER_NOT_FOUND;

import com.schedule.jpa.infra.client.weather.dto.WeatherResponse;
import com.schedule.jpa.service.ScheduleApplicationException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final WebClient webClient;

    public WeatherResponse getWeather(final String date) {
        final List<WeatherResponse> responses = webClient.get()
                .uri("/f-api/weather.json")
                .retrieve()
                .bodyToFlux(WeatherResponse.class)
                .collectList()
                .block();

        return Objects.requireNonNull(responses).stream()
                .filter(response -> response.date().equals(date))
                .findFirst()
                .orElseThrow(() -> new ScheduleApplicationException(WEATHER_NOT_FOUND));
    }
}
