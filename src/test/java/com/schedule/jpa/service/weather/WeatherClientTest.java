package com.schedule.jpa.service.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.schedule.jpa.infra.client.weather.WeatherClient;
import com.schedule.jpa.infra.client.weather.dto.WeatherResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
class WeatherClientTest {

    @Mock
    private WebClient webClient;

    @Mock
    private RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RequestHeadersSpec requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    @InjectMocks
    private WeatherClient weatherClient;

    @Test
    @DisplayName("날씨를 가져온다.")
    void getWeather_success() {
        // given
        final String date = "10-15";

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/f-api/weather.json")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(WeatherResponse.class))
                .thenReturn(Flux.fromIterable(List.of(
                        new WeatherResponse("10-14", "Sunny"),
                        new WeatherResponse("10-15", "Humid")
                )));

        // when
        final WeatherResponse response = weatherClient.getWeather(date);

        // then
        assertThat(response.weather()).isEqualTo("Humid");
    }
}
