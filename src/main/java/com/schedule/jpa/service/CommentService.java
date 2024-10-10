package com.schedule.jpa.service;

import static com.schedule.jpa.controller.exception.ErrorCodes.COMMENT_NOT_FOUND;
import static com.schedule.jpa.controller.exception.ErrorCodes.COMMENT_NOT_FOUND_IN_SCHEDULE;
import static com.schedule.jpa.controller.exception.ErrorCodes.SCHEDULE_NOT_FOUND;

import com.schedule.jpa.controller.comment.dto.CommentSaveRequest;
import com.schedule.jpa.controller.comment.dto.CommentSaveResponse;
import com.schedule.jpa.controller.comment.dto.CommentUpdateRequest;
import com.schedule.jpa.controller.comment.dto.CommentUpdateResponse;
import com.schedule.jpa.domain.comment.Comment;
import com.schedule.jpa.domain.comment.CommentRepository;
import com.schedule.jpa.domain.schedule.Schedule;
import com.schedule.jpa.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentSaveResponse create(final Long scheduleId, final CommentSaveRequest request) {
        final Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleApplicationException(SCHEDULE_NOT_FOUND));
        final Comment comment = Comment.of(request.content(), request.username(), schedule);
        commentRepository.save(comment);
        schedule.addComment(comment);
        return CommentSaveResponse.from(comment);
    }

    @Transactional
    public CommentUpdateResponse update(
            final CommentUpdateRequest request,
            final Long scheduleId,
            final Long commentId
    ) {
        final Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleApplicationException(SCHEDULE_NOT_FOUND));
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ScheduleApplicationException(COMMENT_NOT_FOUND));

        if (!schedule.isContainsComment(comment)) {
            throw new ScheduleApplicationException(COMMENT_NOT_FOUND_IN_SCHEDULE);
        }

        comment.update(request.content());
        return CommentUpdateResponse.from(comment);
    }
}
