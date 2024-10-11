package com.schedule.jpa.service;

import com.schedule.jpa.controller.exception.ErrorCodes;
import com.schedule.jpa.controller.user.dto.UserReadResponse;
import com.schedule.jpa.controller.user.dto.UserSaveRequest;
import com.schedule.jpa.controller.user.dto.UserSaveResponse;
import com.schedule.jpa.domain.user.User;
import com.schedule.jpa.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSaveResponse create(final UserSaveRequest request) {
        final User user = User.of(request.name(), request.email());
        final User savedUser = userRepository.save(user);
        return UserSaveResponse.from(savedUser);
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
