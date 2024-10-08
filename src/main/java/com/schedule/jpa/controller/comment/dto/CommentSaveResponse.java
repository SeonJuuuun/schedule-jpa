package com.schedule.jpa.controller.comment.dto;

import com.schedule.jpa.domain.comment.Comment;
import java.time.LocalDateTime;

public record CommentSaveResponse(
        String content,
        LocalDateTime createdAt,
        String username
) {
    public static CommentSaveResponse from(final Comment comment) {
        return new CommentSaveResponse(comment.getContent(), comment.getCreatedAt(), comment.getUsername());
    }
}
