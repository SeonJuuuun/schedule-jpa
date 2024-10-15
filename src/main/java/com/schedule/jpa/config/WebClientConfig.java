package com.schedule.jpa.config;

import static org.springframework.web.reactive.function.client.WebClient.Builder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(final Builder builder) {
        return builder.baseUrl("https://f-api.github.io").build();
    }
}
