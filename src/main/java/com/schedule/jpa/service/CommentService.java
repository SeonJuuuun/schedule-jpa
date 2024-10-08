package com.schedule.jpa.service;

import com.schedule.jpa.controller.comment.dto.CommentSaveRequest;
import com.schedule.jpa.controller.comment.dto.CommentSaveResponse;
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
        final Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        final Comment comment = Comment.of(request.content(), request.username(), schedule);
        commentRepository.save(comment);
        schedule.addComment(comment);
        return CommentSaveResponse.from(comment);
    }
}
