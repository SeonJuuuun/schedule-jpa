package com.schedule.jpa.controller.comment.dto;

import com.schedule.jpa.domain.comment.Comment;
import java.time.LocalDateTime;

public record CommentReadResponse(
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String username
) {
    public static CommentReadResponse from(final Comment comment) {
        return new CommentReadResponse(comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt(),
                comment.getUsername());
    }
}
