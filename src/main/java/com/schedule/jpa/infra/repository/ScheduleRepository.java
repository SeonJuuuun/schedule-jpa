package com.schedule.jpa.infra.repository;

import com.schedule.jpa.domain.schedule.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Page<Schedule> findAllByOrderByUpdatedAtDesc(final Pageable pageable);
}
