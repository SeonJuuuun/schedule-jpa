package com.schedule.jpa.controller.comment;

import com.schedule.jpa.controller.comment.dto.CommentSaveRequest;
import com.schedule.jpa.controller.comment.dto.CommentSaveResponse;
import com.schedule.jpa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{scheduleId}/comment")
    public ResponseEntity<CommentSaveResponse> createComment(
            @PathVariable final Long scheduleId,
            @RequestBody final CommentSaveRequest request
    ) {
        final CommentSaveResponse response = commentService.create(scheduleId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
