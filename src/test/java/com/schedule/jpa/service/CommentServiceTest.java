package com.schedule.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.schedule.jpa.controller.comment.dto.CommentSaveRequest;
import com.schedule.jpa.controller.comment.dto.CommentSaveResponse;
import com.schedule.jpa.controller.comment.dto.CommentUpdateRequest;
import com.schedule.jpa.controller.comment.dto.CommentUpdateResponse;
import com.schedule.jpa.domain.comment.Comment;
import com.schedule.jpa.domain.schedule.Schedule;
import com.schedule.jpa.domain.user.Role;
import com.schedule.jpa.domain.user.User;
import com.schedule.jpa.infra.repository.CommentRepository;
import com.schedule.jpa.infra.repository.ScheduleRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("댓글 생성 성공")
    void create_success() {
        // given
        final Long scheduleId = 1L;
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final CommentSaveRequest request = new CommentSaveRequest("Test content", "Test username");
        final Schedule schedule = Schedule.of(user, "테스트 일정 제목", "테스트 날씨", "테스트 일정 내용");
        final Comment comment = Comment.of(request.content(), request.username(), schedule);

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // when
        final CommentSaveResponse response = commentService.create(scheduleId, request);

        // then
        assertThat(response.content()).isEqualTo(comment.getContent());
        assertThat(response.username()).isEqualTo(comment.getUsername());
    }

    @Test
    @DisplayName("존재하지 않는 스케줄로 댓글 생성 시 예외 발생")
    void create_scheduleNotFound() {
        // given
        final Long scheduleId = 1L;
        final CommentSaveRequest request = new CommentSaveRequest("테스트 댓글 내용", "테스트 댓글 유저명");

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentService.create(scheduleId, request)).isInstanceOf(
                ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void update_success() {
        // given
        final Long scheduleId = 1L;
        final Long commentId = 1L;
        final CommentUpdateRequest updateRequest = new CommentUpdateRequest("수정할 내용");
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "테스트 일정 제목", "테스트 날씨", "테스트 일정 내용");
        final Comment comment = Comment.of("테스트 댓글 내용", "테스트 댓글 유저명", schedule);

        schedule.addComment(comment);

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // when
        final CommentUpdateResponse response = commentService.update(updateRequest, scheduleId, commentId);

        // then
        assertThat(response.content()).isEqualTo("수정할 내용");
    }

    @Test
    @DisplayName("존재하지 않는 댓글 수정 시 예외 발생")
    void update_commentNotFound() {
        // given
        final Long scheduleId = 1L;
        final Long commentId = 1L;
        final CommentUpdateRequest updateRequest = new CommentUpdateRequest("수정할 내용");
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "테스트 일정 제목", "테스트 날씨", "테스트 일정 내용");

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentService.update(updateRequest, scheduleId, commentId))
                .isInstanceOf(ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("댓글이 스케줄에 포함되어 있지 않으면 수정 시 예외 발생")
    void update_commentNotFoundInSchedule() {
        // given
        final Long scheduleId = 1L;
        final Long commentId = 1L;
        final CommentUpdateRequest updateRequest = new CommentUpdateRequest("수정할 내용");
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "테스트 일정 제목", "테스트 날씨", "테스트 일정 내용");
        final Comment comment = Comment.of("테스트 댓글 내용", "테스트 댓글 유저명", schedule);

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // when & then
        assertThatThrownBy(() -> commentService.update(updateRequest, scheduleId, commentId))
                .isInstanceOf(ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void delete_success() {
        // given
        final Long scheduleId = 1L;
        final Long commentId = 1L;
        final CommentUpdateRequest updateRequest = new CommentUpdateRequest("수정할 내용");
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "테스트 일정 제목", "테스트 날씨", "테스트 일정 내용");
        final Comment comment = Comment.of("테스트 댓글 내용", "테스트 댓글 유저명", schedule);

        schedule.addComment(comment);

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // when
        commentService.delete(scheduleId, commentId);

        // then
        verify(commentRepository).deleteById(commentId);
    }

    @Test
    @DisplayName("존재하지 않는 스케줄로 댓글 삭제 시 예외 발생")
    void delete_scheduleNotFound() {
        // given
        final Long scheduleId = 1L;
        final Long commentId = 1L;

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentService.delete(scheduleId, commentId))
                .isInstanceOf(ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("존재하지 않는 댓글 삭제 시 예외 발생")
    void delete_commentNotFound() {
        // given
        final Long scheduleId = 1L;
        final Long commentId = 1L;

        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "테스트 일정 제목", "테스트 날씨", "테스트 일정 내용");

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentService.delete(scheduleId, commentId))
                .isInstanceOf(ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("댓글이 스케줄에 포함되어 있지 않으면 삭제 시 예외 발생")
    void delete_commentNotFoundInSchedule() {
        // given
        final Long scheduleId = 1L;
        final Long commentId = 1L;
        final User user = User.of("테스트 유저", "test", Role.GENERAL, "test@gmail.com");
        final Schedule schedule = Schedule.of(user, "테스트 일정 제목", "테스트 날씨", "테스트 일정 내용");
        final Comment comment = Comment.of("테스트 댓글 내용", "테스트 댓글 유저명", schedule);

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // when & then
        assertThatThrownBy(() -> commentService.delete(scheduleId, commentId))
                .isInstanceOf(ScheduleApplicationException.class);
    }
}
