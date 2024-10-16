package com.schedule.jpa.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.schedule.jpa.config.PasswordEncoder;
import com.schedule.jpa.domain.schedule.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("사용자 정보 수정 성공")
    void update_success() {
        // given
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");

        final String updateName = "Updated User";
        final String updateEmail = "updated@example.com";

        // when
        user.update(updateName, updateEmail);

        // then
        assertThat(user.getName()).isEqualTo(updateName);
        assertThat(user.getEmail()).isEqualTo(updateEmail);
    }

    @Test
    @DisplayName("일정 추가 성공")
    void addWriteSchedules_success() {
        // given
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "Schedule Title", "Sunny", "Schedule Content");

        // when
        user.addWriteSchedules(schedule);

        // then
        assertThat(user.getWriteSchedules()).contains(schedule);
    }

    @Test
    @DisplayName("비밀번호 검증 성공")
    void isValidPassword_success() {
        // given
        final PasswordEncoder passwordEncoder = new PasswordEncoder();
        final String initPassword = "test";

        final String encodedPassword = passwordEncoder.encode(initPassword);

        final User user = User.of("테스트 유저", encodedPassword, Role.GENERAL, "test@gmail.com");
        final String correctPassword = "test";
        final String incorrectPassword = "wrongPassword";

        assertThat(user.isValidPassword(correctPassword, passwordEncoder)).isTrue();
        assertThat(user.isValidPassword(incorrectPassword, passwordEncoder)).isFalse();
    }

    @Test
    @DisplayName("사용자가 관리자면 true를 반환한다.")
    void isAdmin_success() {
        // given
        final User adminUser = User.of("Admin User", "admin_password", Role.ADMIN, "admin@example.com");

        // when
        final boolean isAdmin = adminUser.isAdmin();

        // then
        assertThat(isAdmin).isTrue();
    }

    @Test
    @DisplayName("사용자가 관리자가 아니면 false를 반환한다.")
    void isAdmin_fail() {
        // given
        final User generalUser = User.of("Admin User", "admin_password", Role.GENERAL, "admin@example.com");

        // when
        boolean isAdmin = generalUser.isAdmin();

        // then
        assertThat(isAdmin).isFalse();
    }
}
