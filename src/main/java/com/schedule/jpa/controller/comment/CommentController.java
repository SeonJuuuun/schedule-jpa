package com.schedule.jpa.controller.comment;

import com.schedule.jpa.controller.comment.dto.CommentSaveRequest;
import com.schedule.jpa.controller.comment.dto.CommentSaveResponse;
import com.schedule.jpa.controller.comment.dto.CommentUpdateRequest;
import com.schedule.jpa.controller.comment.dto.CommentUpdateResponse;
import com.schedule.jpa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/{scheduleId}/{commentId}")
    public ResponseEntity<CommentUpdateResponse> updateComment(
            @RequestBody final CommentUpdateRequest request,
            @PathVariable final Long scheduleId,
            @PathVariable final Long commentId
    ) {
        final CommentUpdateResponse response = commentService.update(request, scheduleId, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{scheduleId}/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable final Long scheduleId,
            @PathVariable final Long commentId
    ) {
        commentService.delete(scheduleId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
