package com.schedule.jpa.domain.user;

import com.schedule.jpa.config.PasswordEncoder;
import com.schedule.jpa.domain.BaseEntity;
import com.schedule.jpa.domain.comment.Comment;
import com.schedule.jpa.domain.schedule.Schedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Schedule> writeSchedules = new ArrayList<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Schedule> schedules = new ArrayList<>();

    public User(final String name, final String password, final Role role, final String email) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public static User of(final String name, final String password, final Role role, final String email) {
        return new User(name, password, role, email);
    }

    public void addWriteSchedules(final Schedule schedule) {
        writeSchedules.add(schedule);
    }

    public boolean isValidPassword(final String password, final PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }
}
