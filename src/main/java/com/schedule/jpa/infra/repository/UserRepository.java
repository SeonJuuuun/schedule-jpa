package com.schedule.jpa.infra.repository;

import com.schedule.jpa.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(final String email);
}
