package com.keck.pattern_tracker_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Counter {

    @Id
    @GeneratedValue
    private Long ID;

    private String name;
    private int currentValue = 0;
    // Holds info on where to render counter
    private int pageNumber = 1;
    private double position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pattern_id", nullable = false)
    private Pattern pattern;
}
