package com.schedule.jpa.service;

import com.schedule.jpa.config.PasswordEncoder;
import com.schedule.jpa.controller.exception.ErrorCodes;
import com.schedule.jpa.controller.user.dto.UserReadResponse;
import com.schedule.jpa.controller.user.dto.UserSaveRequest;
import com.schedule.jpa.controller.user.dto.UserSaveResponse;
import com.schedule.jpa.domain.jwt.Jwt;
import com.schedule.jpa.domain.jwt.JwtProvider;
import com.schedule.jpa.domain.user.User;
import com.schedule.jpa.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserSaveResponse create(final UserSaveRequest request) {
        final String password = passwordEncoder.encode(request.password());
        final User user = User.of(request.name(), password, request.email());
        final User savedUser = userRepository.save(user);
        final Jwt accessToken = jwtProvider.createToken(savedUser.getId());
        return UserSaveResponse.from(savedUser, accessToken);
    }

    public UserReadResponse findUser(final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ScheduleApplicationException(ErrorCodes.USER_NOT_FOUND));
        return UserReadResponse.from(user);
    }

    public void delete(final Long userId) {
        userRepository.deleteById(userId);
    }
}
