package com.schedule.jpa.domain.schedule;

import com.schedule.jpa.domain.BaseEntity;
import com.schedule.jpa.domain.comment.Comment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String title;

    private String content;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Schedule(final String username, final String title, final String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }

    public static Schedule of(final String username, final String title, final String content) {
        return new Schedule(username, title, content);
    }

    public void update(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public void addComment(final Comment comment) {
        comments.add(comment);
    }

    public boolean isContainsComment(final Comment comment) {
        return comments.contains(comment);
    }
}
