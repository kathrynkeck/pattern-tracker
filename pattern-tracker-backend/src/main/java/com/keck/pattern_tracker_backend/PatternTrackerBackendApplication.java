package com.keck.pattern_tracker_backend;

import com.keck.pattern_tracker_backend.model.Pattern;
import com.keck.pattern_tracker_backend.repository.PatternRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PatternTrackerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatternTrackerBackendApplication.class, args);
	}

}
