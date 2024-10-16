package com.schedule.jpa.domain.comment;

import static org.assertj.core.api.Assertions.assertThat;

import com.schedule.jpa.domain.schedule.Schedule;
import com.schedule.jpa.domain.user.Role;
import com.schedule.jpa.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

    @Test
    @DisplayName("content의 내용을 바꾼다")
    void update_test() {
        // given
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "title", "weather", "content");
        final Comment comment = Comment.of("before", "username", schedule);
        final String afterContent = "after";

        // when
        comment.update(afterContent);

        // then
        assertThat(comment.getContent()).isEqualTo(afterContent);
    }
}
