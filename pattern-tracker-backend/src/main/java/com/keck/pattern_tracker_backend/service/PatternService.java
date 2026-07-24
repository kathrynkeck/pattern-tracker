package com.keck.pattern_tracker_backend.service;

import com.keck.pattern_tracker_backend.model.Pattern;
import com.keck.pattern_tracker_backend.repository.PatternRepository;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PatternService {

    @Autowired
    private PatternRepository patternRepository;

    // Local folder where pdfs are stored
    private final Path rootLocation = Paths.get("patterns");

    public Pattern savePattern(String title, MultipartFile file, String description) {
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
            LocalDateTime uploadedDateTime = LocalDateTime.now();
            Pattern pattern = new Pattern();
            pattern.setTitle(title);
            pattern.setFileStoragePath(destinationFile.toString());
            pattern.setDescription(description);
            pattern.setUploadedDateTime(uploadedDateTime);
            pattern.setEditedDateTime(uploadedDateTime);
            pattern.setIsWip(false);

            return patternRepository.save(pattern);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public List<Pattern> getAllPatterns() {
        return patternRepository.findAll(Sort.by("editedDateTime").descending());
    }

    public Pattern getPatternById(Long id) {
        return patternRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pattern not found with id: " + id));
    }

    public Pattern setWipTrue(Long id) {
        Pattern ptrn = patternRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pattern not found with id: " + id));
        ptrn.setIsWip(true);
        ptrn.setEditedDateTime(LocalDateTime.now());
        return patternRepository.save(ptrn);
    }

    public Pattern setWipFalse(Long id) {
        Pattern ptrn = patternRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pattern not found with id: " + id));
        ptrn.setIsWip(false);
        ptrn.setEditedDateTime(LocalDateTime.now());
        return patternRepository.save(ptrn);
    }

    public Pattern updateDescription(Long id, String description) {
        Pattern ptrn = patternRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pattern not found with id: " + id));
        ptrn.setDescription(description);
        ptrn.setEditedDateTime(LocalDateTime.now());
        return patternRepository.save(ptrn);
    }

    public Resource loadPatternFileAsResource(Long id) {
        Pattern pattern = getPatternById(id);

        try {
            Path filePath = Paths.get(pattern.getFileStoragePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Physical file not found or unreadable at path: " + pattern.getFileStoragePath());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error reading file path format", e);
        }
    }
}
