package dev.drew.runnerz;

import dev.drew.runnerz.run.Location;
import dev.drew.runnerz.run.Run;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        logger.info("Application Started Successfully");
	}

    @Bean
    CommandLineRunner runner() {
        return args -> {
            Run run = new Run(1, "First Run", LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS), 5, Location.OUTDOOR);
            logger.info("Run: {}", run);
        };
    }
}