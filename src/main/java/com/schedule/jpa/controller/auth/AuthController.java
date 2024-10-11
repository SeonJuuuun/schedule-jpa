package com.schedule.jpa.controller.auth;

import com.schedule.jpa.controller.auth.dto.LoginRequest;
import com.schedule.jpa.controller.auth.dto.LoginResponse;
import com.schedule.jpa.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid final LoginRequest request,
            final HttpServletResponse response
    ) {
        final LoginResponse loginResponse = authService.login(request);
        response.addHeader("Authorization", "Bearer " + loginResponse.token());
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
}
