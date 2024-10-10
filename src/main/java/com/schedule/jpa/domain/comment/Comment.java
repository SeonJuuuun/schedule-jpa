package com.schedule.jpa.domain.comment;

import com.schedule.jpa.domain.BaseEntity;
import com.schedule.jpa.domain.schedule.Schedule;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment(final String content, final String username, final Schedule schedule) {
        this.content = content;
        this.username = username;
        this.schedule = schedule;
    }

    public static Comment of(final String content, final String username, final Schedule schedule) {
        return new Comment(content, username, schedule);
    }

    public void update(final String content) {
        this.content = content;
    }
}
