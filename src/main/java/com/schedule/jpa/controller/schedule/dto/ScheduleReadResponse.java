package com.schedule.jpa.controller.schedule.dto;

import com.schedule.jpa.controller.comment.dto.CommentReadResponse;
import com.schedule.jpa.domain.schedule.Schedule;
import java.time.LocalDateTime;
import java.util.List;

public record ScheduleReadResponse(
        String username,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<CommentReadResponse> comments

) {
    public static ScheduleReadResponse from(final Schedule schedule) {
        final List<CommentReadResponse> comments = schedule.getComments().stream()
                .map(CommentReadResponse::from)
                .toList();
        return new ScheduleReadResponse(schedule.getUsername(), schedule.getTitle(), schedule.getContent(),
                schedule.getCreatedAt(), schedule.getUpdatedAt(), comments);
    }
}
