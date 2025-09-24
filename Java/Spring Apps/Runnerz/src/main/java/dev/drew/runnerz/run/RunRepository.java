package dev.drew.runnerz.run;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {
    private final List<Run> runs = new ArrayList<>();

    List<Run> findAll() {
        return runs;
    }

    Optional<Run> findById(Integer id) {
        return runs.stream()
                .filter(run -> run.id() == id)
                .findFirst();
    }

    void create(Run run) {
        runs.add(run);
    }

    void update(Run run, Integer id) {
        Optional<Run> existingRun = findById(id);
        if (existingRun.isPresent()) {
            runs.set(runs.indexOf(existingRun.get()), run);
        }
    }

    void delete(Integer id) {
        runs.removeIf(run -> run.id() == id);
    }

    @PostConstruct
    private void init() {
        runs.add(new Run(1, "Wednesday Morning Run", LocalDateTime.now().minusHours(8), LocalDateTime.now(), 3, Location.INDOOR));
        runs.add(new Run(2, "Friday Afternoon Run", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(1), 6, Location.INDOOR));
    }
}
