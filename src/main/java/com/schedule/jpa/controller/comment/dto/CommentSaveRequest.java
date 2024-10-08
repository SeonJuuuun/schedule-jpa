package com.schedule.jpa.controller.comment.dto;

public record CommentSaveRequest (
        String content,
        String username
){
}
