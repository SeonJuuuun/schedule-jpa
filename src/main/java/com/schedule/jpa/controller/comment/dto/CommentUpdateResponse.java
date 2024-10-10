package com.schedule.jpa.controller.comment.dto;

import com.schedule.jpa.domain.comment.Comment;
import java.time.LocalDateTime;

public record CommentUpdateResponse(
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String username
) {
    public static CommentUpdateResponse from(final Comment comment) {
        return new CommentUpdateResponse(comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt(),
                comment.getUsername());
    }
}
