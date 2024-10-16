package com.schedule.jpa.service.weather;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.schedule.jpa.infra.client.weather.WeatherClient;
import com.schedule.jpa.infra.client.weather.dto.WeatherResponse;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

@ExtendWith(MockitoExtension.class)
class WeatherClientTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private WeatherClient weatherClient;

    @BeforeEach
    void setUp() {
        webClient = WebClient.builder().baseUrl("https://f-api.github.io")
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                                .responseTimeout(Duration.ofMillis(5000))
                                .doOnConnected(connection ->
                                        connection
                                                .addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                )
                ))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Test
    @DisplayName("날씨를 가져온다.")
    void getWeather_success() {
        // given
        final String date = "10-15";

        final List<WeatherResponse> weatherResponses = List.of(
                new WeatherResponse("10-14", "Sunny"),
                new WeatherResponse("10-15", "Humid")
        );

        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        when(responseSpec.bodyToFlux(WeatherResponse.class)).thenReturn(Flux.fromIterable(weatherResponses));

        // when
        final WeatherResponse response = weatherClient.getWeather(date);

        // then
        assertThat(response.weather()).isEqualTo("Humid");
    }
}
