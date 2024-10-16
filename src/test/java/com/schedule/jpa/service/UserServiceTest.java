package com.schedule.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.schedule.jpa.config.PasswordEncoder;
import com.schedule.jpa.controller.user.dto.UserReadResponse;
import com.schedule.jpa.controller.user.dto.UserSaveRequest;
import com.schedule.jpa.controller.user.dto.UserSaveResponse;
import com.schedule.jpa.controller.user.dto.UserUpdateRequest;
import com.schedule.jpa.controller.user.dto.UserUpdateResponse;
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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("유저 회원가입 테스트")
    void create_user() {
        // given
        final UserSaveRequest request = new UserSaveRequest("테스트 이름", "test pw", "test@gmail.com", "GENERAL");
        final User user = User.of(request.name(), request.password(), Role.from(request.role()), request.email());
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        final UserSaveResponse response = userService.create(request);

        // then
        assertThat(response.name()).isEqualTo(user.getName());
        assertThat(response.email()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("존재하지 않는 유저 조회 테스트")
    void find_non_exist_user() {
        // given
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.findUser(1L)).isInstanceOf(ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("존재하는 유저 조회 테스트")
    void find_exist_user() {
        // given
        final User user = User.of("테스트 이름", "test pw", Role.GENERAL, "test@gmail.com");

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        // when
        final UserReadResponse response = userService.findUser(1L);

        // then
        assertThat(response.name()).isEqualTo("테스트 이름");
        assertThat(response.email()).isEqualTo("test@gmail.com");
        assertThat(response.role()).isEqualTo("GENERAL");
    }

    @Test
    @DisplayName("존재하지 않는 유저 조회 테스트")
    void update_non_exist_user() {
        // given
        final UserUpdateRequest request = new UserUpdateRequest("테스트 이름", "테스트 이메일");
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        //

        // when & then
        assertThatThrownBy(() -> userService.update(request, 1L, 1L)).isInstanceOf(ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("수정 권한이 없는 유저 테스트")
    void update_not_owner_user() {
        //given
        final Long userId = 1L;
        final Long loginId = 2L;
        final UserUpdateRequest request = new UserUpdateRequest("테스트 이름", "테스트 이메일");

        // when & then
        assertThatThrownBy(() -> userService.update(request, userId, loginId)).isInstanceOf(
                ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("유저 수정 테스트")
    void update_user() {
        //given
        final Long userId = 1L;
        final Long loginId = 1L;
        final UserUpdateRequest request = new UserUpdateRequest("수정된 이름", "수정된 이메일");
        final User user = User.of("테스트이름", "테스트 비밀번호", Role.from("ADMIN"), "테스트 이메일");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //when
        final UserUpdateResponse response = userService.update(request, userId, loginId);

        //then
        assertThat(response.name()).isEqualTo("수정된 이름");
        assertThat(response.email()).isEqualTo("수정된 이메일");
    }
}
