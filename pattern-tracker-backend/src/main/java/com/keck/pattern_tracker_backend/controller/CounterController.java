package com.keck.pattern_tracker_backend.controller;

import com.keck.pattern_tracker_backend.model.Counter;
import com.keck.pattern_tracker_backend.service.CounterService;
import com.keck.pattern_tracker_backend.service.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/counters")
@CrossOrigin(origins = "http://localhost:4200")
public class CounterController {
    @Autowired
    CounterService counterService;
    @Autowired
    PatternService patternService;

    @PostMapping("/{patternId}")
    public ResponseEntity<Counter> createCounter(@PathVariable Long patternId,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("page_num") int pageNum,
                                                 @RequestParam("position") double position){
        Counter savedCounter = counterService.saveCounter(patternId, name, pageNum, position);
        return ResponseEntity.ok(savedCounter);
    }
}
