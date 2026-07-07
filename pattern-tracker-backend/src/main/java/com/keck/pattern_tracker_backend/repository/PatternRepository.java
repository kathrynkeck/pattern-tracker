package com.keck.pattern_tracker_backend.repository;

import com.keck.pattern_tracker_backend.model.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatternRepository extends JpaRepository<Pattern, Long> {

}
