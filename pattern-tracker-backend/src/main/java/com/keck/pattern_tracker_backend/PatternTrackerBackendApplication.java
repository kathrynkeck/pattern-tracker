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

    @Bean
    public CommandLineRunner demo(PatternRepository repository){
        return (args) -> {
            System.out.println("===== DB CONNECTION TEST START =====");

            // create mock pattern
            Pattern testPattern = new Pattern();
            testPattern.setTitle("Test Cable Sweater Pattern");
            testPattern.setFileStoragePath("/mock/path/pattern.pdf");

            // save it
            repository.save(testPattern);
            System.out.println("Successfully saved test pattern to DB");

            // read it back
            System.out.println("Fetching patterns from DB ...");
            repository.findAll().forEach(pattern -> {
                System.out.println("Found Pattern ID: " + pattern.getId() + " | Title: " + pattern.getTitle());
            });

            System.out.println("===== DB CONNECTION TEST SUCCESS =====");
        };
    }

}
