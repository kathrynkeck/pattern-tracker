package com.keck.pattern_tracker_backend.controller;

import com.keck.pattern_tracker_backend.model.Pattern;
import com.keck.pattern_tracker_backend.service.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/patterns")
@CrossOrigin(origins = "http://localhost:4200")
public class PatternController {

    @Autowired
    private PatternService patternService;

    @PostMapping("/upload")
    public ResponseEntity<Pattern> uploadPattern(@RequestParam("title") String title,
                                                 @RequestParam("file") MultipartFile file,
                                                 @RequestParam("description") String description) {
        Pattern savedPattern = patternService.savePattern(title, file, description);
        return ResponseEntity.ok(savedPattern);
    }

    @GetMapping("")
    public ResponseEntity<List<Pattern>> getAllPatterns() {
        List<Pattern> patterns = patternService.getAllPatterns();
        return ResponseEntity.ok(patterns);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Pattern> getPattern(@PathVariable Long fileId){
        try {
            Pattern pattern = patternService.getPatternById(fileId);
            return ResponseEntity.ok(pattern);
        } catch (RuntimeException e) {
            // Returns a 404 Not Found status if the ID doesn't exist
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadPatternFile(@PathVariable Long id) {

        Resource fileResource = patternService.loadPatternFileAsResource(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }

    @PatchMapping("/{id}/wip")
    public ResponseEntity<Pattern> setPatternToWip(@PathVariable Long id){
        Pattern pattern = patternService.setWipTrue(id);
        return ResponseEntity.ok(pattern);
    }

    @PatchMapping("/{id}/notWip")
    public ResponseEntity<Pattern> setPatternToNotWip(@PathVariable Long id){
        Pattern pattern = patternService.setWipFalse(id);
        return ResponseEntity.ok(pattern);
    }

    @PatchMapping("{id}/description")
    public ResponseEntity<Pattern> updateDescription(@PathVariable Long id,
                                                     @RequestParam("description") String description){
        Pattern pattern = patternService.updateDescription(id, description);
        return ResponseEntity.ok(pattern);
    }
}
