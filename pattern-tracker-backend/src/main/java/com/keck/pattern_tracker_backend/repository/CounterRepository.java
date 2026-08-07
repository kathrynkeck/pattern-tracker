package com.keck.pattern_tracker_backend.repository;

import com.keck.pattern_tracker_backend.model.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterRepository extends JpaRepository<Counter, Long> {
}
