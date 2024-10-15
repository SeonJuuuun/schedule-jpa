package com.schedule.jpa.infra.client.weather;

import com.schedule.jpa.infra.client.weather.dto.WeatherResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class WeatherClient {

    private final WebClient webClient;

    public WeatherResponse getWeather(final String date) {
        final WeatherResponse[] responses = webClient.get()
                .uri("/f-api/weather.json")
                .retrieve()
                .bodyToMono(WeatherResponse[].class)
                .block();

        return Arrays.stream(responses)
                .filter(response -> response.date().equals(date))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("날씨 정보를 찾을 수 없습니다."));
    }
}
