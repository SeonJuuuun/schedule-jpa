package com.schedule.jpa.controller.user;

import com.schedule.jpa.common.resolver.AuthenticationUserId;
import com.schedule.jpa.controller.user.dto.UserReadResponse;
import com.schedule.jpa.controller.user.dto.UserSaveRequest;
import com.schedule.jpa.controller.user.dto.UserSaveResponse;
import com.schedule.jpa.controller.user.dto.UserUpdateRequest;
import com.schedule.jpa.controller.user.dto.UserUpdateResponse;
import com.schedule.jpa.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserSaveResponse> createUser(@RequestBody @Valid final UserSaveRequest request) {
        final UserSaveResponse response = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserReadResponse> readUser(@PathVariable final Long userId) {
        final UserReadResponse response = userService.findUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @RequestBody final UserUpdateRequest request,
            @PathVariable final Long userId,
            @AuthenticationUserId final Long loginId
    ) {
        final UserUpdateResponse response = userService.update(request, userId, loginId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable final Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
