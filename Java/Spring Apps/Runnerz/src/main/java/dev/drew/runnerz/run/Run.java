package dev.drew.runnerz.run;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

/**
 * Creates a Run Object
 * @param id ID of the Run
 * @param title Title of the Run
 * @param startedOn Date and Time the Run was Started
 * @param completedOn Date and Time the Run was Completed
 * @param miles Miles run in the Run
 * @param location Location of the Run
 */
public record Run(@Id Integer id,
                  @NotEmpty String title,
                  LocalDateTime startedOn,
                  LocalDateTime completedOn,
                  @Positive Integer miles,
                  Location location,
                  @Version Integer version) {
    public Run {
        if (startedOn != null && completedOn != null && !completedOn.isAfter(startedOn)) {
            throw new IllegalArgumentException("CompletedOn Must be After StartedOn");
        }
    }
}
