package com.schedule.jpa.domain.schedule;

import com.schedule.jpa.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public Schedule(final String username, final String title, final String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }

    public static Schedule of(final String username, final String title, final String content) {
        return new Schedule(username, title, content);
    }
}
