package com.schedule.jpa.service;

import static com.schedule.jpa.controller.exception.ErrorCodes.USER_NOT_FOUND;
import static com.schedule.jpa.controller.exception.ErrorCodes.USER_VERIFY_OWNER;

import com.schedule.jpa.config.PasswordEncoder;
import com.schedule.jpa.controller.user.dto.UserReadResponse;
import com.schedule.jpa.controller.user.dto.UserSaveRequest;
import com.schedule.jpa.controller.user.dto.UserSaveResponse;
import com.schedule.jpa.controller.user.dto.UserUpdateRequest;
import com.schedule.jpa.controller.user.dto.UserUpdateResponse;
import com.schedule.jpa.domain.user.Role;
import com.schedule.jpa.domain.user.User;
import com.schedule.jpa.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSaveResponse create(final UserSaveRequest request) {
        final String password = passwordEncoder.encode(request.password());
        final Role role = Role.from(request.role());
        final User user = User.of(request.name(), password, role, request.email());
        final User savedUser = userRepository.save(user);
        return UserSaveResponse.from(savedUser);
    }

    public UserReadResponse findUser(final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ScheduleApplicationException(USER_NOT_FOUND));
        return UserReadResponse.from(user);
    }

    @Transactional
    public UserUpdateResponse update(final UserUpdateRequest request, final Long userId, final Long loginId) {
        if (!userId.equals(loginId)) {
            throw new ScheduleApplicationException(USER_VERIFY_OWNER);
        }
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ScheduleApplicationException(USER_NOT_FOUND));
        user.update(request.name(), request.email());
        return UserUpdateResponse.from(user);
    }

    public void delete(final Long userId) {
        userRepository.deleteById(userId);
    }
}
