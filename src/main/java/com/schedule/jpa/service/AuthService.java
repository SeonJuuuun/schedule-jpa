package com.schedule.jpa.service;

import static com.schedule.jpa.controller.exception.ErrorCodes.INVALID_EMAIL;
import static com.schedule.jpa.controller.exception.ErrorCodes.INVALID_PASSWORD;

import com.schedule.jpa.config.PasswordEncoder;
import com.schedule.jpa.controller.auth.dto.LoginRequest;
import com.schedule.jpa.controller.auth.dto.LoginResponse;
import com.schedule.jpa.domain.jwt.JwtProvider;
import com.schedule.jpa.domain.user.User;
import com.schedule.jpa.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    public LoginResponse login(final LoginRequest request) {
        final User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ScheduleApplicationException(INVALID_EMAIL));

        if (!user.isValidPassword(request.password(), encoder)) {
            throw new ScheduleApplicationException(INVALID_PASSWORD);
        }

        final String token = jwtProvider.createToken(user.getId(), user.getRole());
        return new LoginResponse(token);
    }
}
