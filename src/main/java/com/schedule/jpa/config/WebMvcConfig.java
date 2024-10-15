package com.schedule.jpa.config;

import com.schedule.jpa.common.resolver.CurrentUserArgumentResolver;
import com.schedule.jpa.domain.jwt.JwtProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtProvider jwtProvider;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CurrentUserArgumentResolver(jwtProvider));
    }
}
