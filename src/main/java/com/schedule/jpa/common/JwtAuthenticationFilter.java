package com.schedule.jpa.common;

import static com.schedule.jpa.controller.exception.ErrorCodes.TOKEN_NULL_EXCEPTION;

import com.schedule.jpa.controller.exception.ErrorCodes;
import com.schedule.jpa.domain.jwt.JwtProvider;
import com.schedule.jpa.service.ScheduleApplicationException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        final String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);

        final String requestUri = req.getRequestURI();

        if (requestUri.startsWith("/user") || requestUri.startsWith("/auth/login")) {
            chain.doFilter(request, response);
            return;
        }

        if (Objects.isNull(authorizationHeader)) {
            throw new ScheduleApplicationException(TOKEN_NULL_EXCEPTION);
        }

        if (jwtProvider.isTokenExpired(authorizationHeader)) {
            throw new ScheduleApplicationException(ErrorCodes.TOKEN_EXPIRED);
        }

        chain.doFilter(request, response);
    }
}
