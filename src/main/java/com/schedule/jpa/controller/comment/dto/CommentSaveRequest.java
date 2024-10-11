package com.schedule.jpa.controller.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentSaveRequest (
        @NotBlank String content,
        @NotNull String username
){
}
