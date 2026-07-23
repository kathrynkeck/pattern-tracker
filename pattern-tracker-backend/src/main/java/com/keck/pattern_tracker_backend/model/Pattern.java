package com.keck.pattern_tracker_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Pattern {
    @Id
    @GeneratedValue
    private Long id;
    // user entered title of pattern
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    // path where pdf lives on disk
    @Column(name = "file_storage_path", nullable = false)
    private String fileStoragePath;
    // user entered description of pattern
    private String description;
    // date the pattern was uploaded
    @Column(name = "uploaded-date-time", nullable = false)
    private LocalDateTime uploadedDateTime;
    // date the pattern was last edited
    @Column(name = "edited-date-time", nullable = false)
    private LocalDateTime editedDateTime;
    // is the user currently working on this pattern
    private Boolean isWip;

    @OneToMany(mappedBy = "pattern", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Counter> counters = new ArrayList<>();
}
