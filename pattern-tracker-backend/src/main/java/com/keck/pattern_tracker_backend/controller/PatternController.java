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
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Resource> downloadPatternFile(@PathVariable Long id) {

        Resource fileResource = patternService.loadPatternFileAsResource(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }
}
