package com.schedule.jpa.controller.comment.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest (
        @NotBlank String content
){
}
