package com.keck.pattern_tracker_backend.controller;

import com.keck.pattern_tracker_backend.model.Pattern;
import com.keck.pattern_tracker_backend.service.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/patterns")
@CrossOrigin(origins = "http://localhost:4200")
public class PatternController {

    @Autowired
    private PatternService patternService;

    @PostMapping("/upload")
    public ResponseEntity<Pattern> uploadPattern(@RequestParam("title") String title,
                                                 @RequestParam("file") MultipartFile file) {
        Pattern savedPattern = patternService.savePattern(title, file);
        return ResponseEntity.ok(savedPattern);
    }
}
