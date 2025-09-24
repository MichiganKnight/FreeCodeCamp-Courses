package dev.drew.runnerz.run;

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
public record Run(Integer id, String title, LocalDateTime startedOn, LocalDateTime completedOn, Integer miles, Location location) {}
