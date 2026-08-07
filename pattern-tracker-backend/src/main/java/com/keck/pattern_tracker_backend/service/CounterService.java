package com.keck.pattern_tracker_backend.service;

import com.keck.pattern_tracker_backend.model.Counter;
import com.keck.pattern_tracker_backend.model.Pattern;
import com.keck.pattern_tracker_backend.repository.CounterRepository;
import com.keck.pattern_tracker_backend.repository.PatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CounterService {
    @Autowired
    CounterRepository counterRepository;
    @Autowired
    PatternRepository patternRepository;

    public Counter saveCounter(Long patternId, String name, int pageNum, double position) {
        Counter counter = new Counter();
        Pattern pattern = patternRepository.findById(patternId)
                .orElseThrow(() -> new RuntimeException("Pattern not found with id: " + patternId));
        counter.setPattern(pattern);
        counter.setName(name);
        counter.setPageNumber(pageNum);
        counter.setPosition(position);
        Counter savedCounter = counterRepository.save(counter);
        pattern.addCounter(savedCounter);
        return savedCounter;
    }
}