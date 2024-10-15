package com.schedule.jpa.infra.repository;

import com.schedule.jpa.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
