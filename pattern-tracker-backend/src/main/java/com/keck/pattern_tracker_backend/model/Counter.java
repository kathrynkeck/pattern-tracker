package com.keck.pattern_tracker_backend.model;

import jakarta.persistence.*;

@Entity
public class Counter {

    @Id
    @GeneratedValue
    private Long ID;

    private String name;
    private int currentValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pattern_id", nullable = false)
    private Pattern pattern;
}
