package com.keck.pattern_tracker_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Pattern {
    @Id
    @GeneratedValue
    private Long id;
    // user entered title of pattern
    private String title;
    //path where pdf lives on disk
    private String fileStoragePath;

    @OneToMany(mappedBy = "pattern", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Counter> counters = new ArrayList<>();
}
