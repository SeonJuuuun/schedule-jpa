package com.schedule.jpa.domain.user;

import com.schedule.jpa.domain.BaseEntity;
import com.schedule.jpa.domain.comment.Comment;
import com.schedule.jpa.domain.schedule.Schedule;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Schedule> schedules = new ArrayList<>();

    public User(final String name, final String email) {
        this.name = name;
        this.email = email;
    }

    public static User of(final String name, final String email) {
        return new User(name, email);
    }
}
