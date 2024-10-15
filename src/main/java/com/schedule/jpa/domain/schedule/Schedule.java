package com.schedule.jpa.domain.schedule;

import com.schedule.jpa.domain.BaseEntity;
import com.schedule.jpa.domain.comment.Comment;
import com.schedule.jpa.domain.user.User;
import com.schedule.jpa.domain.weather.Weather;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Embedded
    private Weather weather;

    @ManyToMany
    @JoinTable(name = "schedule_user",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

    public Schedule(final User user, final String title, final Weather weather, final String content) {
        this.user = user;
        this.title = title;
        this.weather = weather;
        this.content = content;
    }

    public static Schedule of(final User user, final String title, final Weather weather, final String content) {
        return new Schedule(user, title, weather, content);
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

    public void deleteComment(Comment comment) {
        comments.remove(comment);
    }
}
