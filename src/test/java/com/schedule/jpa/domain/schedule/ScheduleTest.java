package com.schedule.jpa.domain.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import com.schedule.jpa.domain.comment.Comment;
import com.schedule.jpa.domain.user.Role;
import com.schedule.jpa.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScheduleTest {

    @Test
    @DisplayName("일정 제목 및 내용 수정 성공")
    void update_success() {
        // given
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "title", "weather", "content");
        final String updatedTitle = "수정된 제목";
        final String updatedContent = "수정된 내용";

        // when
        schedule.update(updatedTitle, updatedContent);

        // then
        assertThat(schedule.getTitle()).isEqualTo(updatedTitle);
        assertThat(schedule.getContent()).isEqualTo(updatedContent);
    }

    @Test
    @DisplayName("댓글 추가 성공")
    void addComment_success() {
        // given
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "title", "weather", "content");
        final Comment comment = Comment.of("테스트 내용", "테스트 유저명", schedule);

        // when
        schedule.addComment(comment);

        // then
        assertThat(schedule.getComments()).contains(comment);
    }

    @Test
    @DisplayName("댓글 포함 여부 확인 성공")
    void isContainsComment_success() {
        // given
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "title", "weather", "content");
        final Comment comment = Comment.of("테스트 내용", "테스트 유저명", schedule);

        schedule.addComment(comment);

        // when
        final boolean containsComment = schedule.isContainsComment(comment);

        // then
        assertThat(containsComment).isTrue();
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteComment_success() {
        // given
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "title", "weather", "content");
        final Comment comment = Comment.of("테스트 내용", "테스트 유저명", schedule);

        schedule.addComment(comment);

        // when
        schedule.deleteComment(comment);

        // then
        assertThat(schedule.getComments().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("일정 소유자 확인 성공")
    void verifyOwner_success() {
        // given
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "title", "weather", "content");

        // when
        final boolean isOwner = schedule.verifyOwner(user);

        // then
        assertThat(isOwner).isTrue();
    }

    @Test
    @DisplayName("일정 소유자가 아님")
    void verifyOwner_fail() {
        // given
        final User anotherUser = User.of("Another User", "password", Role.GENERAL, "another@example.com");
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "title", "weather", "content");

        // when
        boolean isOwner = schedule.verifyOwner(anotherUser);

        // then
        assertThat(isOwner).isFalse();
    }
}
