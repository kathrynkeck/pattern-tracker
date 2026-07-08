package com.keck.pattern_tracker_backend.service;

import com.keck.pattern_tracker_backend.model.Pattern;
import com.keck.pattern_tracker_backend.repository.PatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class PatternService {

    @Autowired
    private PatternRepository patternRepository;

    // Local folder where pdfs are stored
    private final Path rootLocation = Paths.get("Desktop/pattern-tracker/patterns");

    public Pattern savePattern(String title, MultipartFile file) {
        try {
            // Create directory if it doesn't exist
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            // Save file
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path destinationFile = this.rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            // Add pattern to DB
            Pattern pattern = new Pattern();
            pattern.setTitle(title);
            pattern.setFileStoragePath(destinationFile.toString());

            return patternRepository.save(pattern);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}
