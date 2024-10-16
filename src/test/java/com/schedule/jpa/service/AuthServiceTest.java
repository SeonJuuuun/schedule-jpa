package com.schedule.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.schedule.jpa.config.PasswordEncoder;
import com.schedule.jpa.controller.auth.dto.LoginRequest;
import com.schedule.jpa.controller.auth.dto.LoginResponse;
import com.schedule.jpa.domain.jwt.JwtProvider;
import com.schedule.jpa.domain.user.Role;
import com.schedule.jpa.domain.user.User;
import com.schedule.jpa.infra.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("로그인 성공 시 JWT 토큰을 반환한다")
    void login_success() {
        // given
        final LoginRequest request = new LoginRequest("test@gmail.com", "test");
        final User user = User.of("테스트 이름", "test", Role.from("GENERAL"), "test@gmail.com");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(encoder.matches(request.password(), user.getPassword())).thenReturn(true);
        when(jwtProvider.createToken(user.getId(), user.getRole())).thenReturn("jwtToken");

        // when
        final LoginResponse response = authService.login(request);

        // then
        assertThat(response.token()).isEqualTo("jwtToken");
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인 시도 시 예외를 발생시킨다")
    void login_invalidEmail() {
        // given
        final LoginRequest request = new LoginRequest("test@example.com", "test");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> authService.login(request)).isInstanceOf(ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("비밀번호가 틀리면 예외를 발생시킨다")
    void login_invalidPassword() {
        // given
        final LoginRequest request = new LoginRequest("test@gmail.com", "test");
        final User user = User.of("테스트 이름", "test", Role.from("GENERAL"), "test@gmail.com");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));

        // when & then
        assertThatThrownBy(() -> authService.login(request)).isInstanceOf(ScheduleApplicationException.class);
    }
}
