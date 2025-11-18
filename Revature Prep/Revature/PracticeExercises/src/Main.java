import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    // Declare Static Final Variables
    private static final String OUTPUT_DIR = "out/production/PracticeExercises";
    private static final File SRC_DIR = new File("src");

    private static final Set<String> EXCLUDED_DIRS = Set.of(".idea", "out", "bin");
    private static final Set<String> EXCLUDED_FILES = new HashSet<>(Arrays.asList(
            "Main.java",
            "Bicycle.java",
            "NegativeSpeedException.java",
            "DisplayOverloading.java",
            "Animal.java",
            "EmployeeCount.java",
            "Shape.java",
            "Shapes.java",
            "Person.java",
            "Animals.java",
            "Swimmer.java",
            "SRP.java",
            "OCP.java",
            "LSP.java",
            "ISP.java",
            "DIP.java"
    ));

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        List<Exercise> exercises = discoverExercises();

        if (exercises.isEmpty()) {
            System.out.println("No Java Files Found");
            return;
        }

        // Show Menu
        System.out.println("\n=== Revature Practice Exercises Launcher ===");
        for (int i = 0; i < exercises.size(); i++) {
            System.out.println((i + 1) + ". " + exercises.get(i).displayName);
        }

        System.out.println("Q. Quit");
        System.out.print("Select an Exercise to Run: ");

        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("q")) return;

        try {
            int choice = Integer.parseInt(input) - 1;

            if (choice >= 0 && choice < exercises.size()) {
                runExercise(exercises.get(choice));
            } else {
                System.out.println("Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Choice");
        }
    }

    private static List<Exercise> discoverExercises() {
        List<Exercise> exercises = new ArrayList<>();

        findJavaFiles(Main.SRC_DIR, "", exercises);

        return exercises;
    }

    private static void findJavaFiles(File dir, String packageName, List<Exercise> exercises) {
        if (EXCLUDED_DIRS.contains(dir.getName())) return;

        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                String newPackage = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();

                findJavaFiles(file, newPackage, exercises);
            } else if (file.getName().endsWith(".java") && !EXCLUDED_FILES.contains(file.getName())) {
                String className = file.getName().replace(".java", "");
                String qualifiedName = packageName.isEmpty() ? className : packageName + "." + className;
                String displayName = className + " Exercise";

                exercises.add(new Exercise(qualifiedName, displayName));
            }
        }
    }

    private static void runExercise(Exercise exercise) throws IOException, InterruptedException {
        clearConsole();

        Process run = new ProcessBuilder("java", "-cp", OUTPUT_DIR, exercise.qualifiedName)
                .inheritIO()
                .start();

        run.waitFor();

        System.exit(0);
    }

    private static void clearConsole() {
        try {
            boolean supportsAnsi = System.console() != null && System.getenv("TERM") != null;

            if (supportsAnsi) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            } else {
                for (int i = 0; i < 60; i++) {
                    System.out.println();
                }
            }
        } catch (Exception ignored) {
            for (int i = 0; i < 60; i++) {
                System.out.println();
            }
        }
    }

    static class Exercise {
        String qualifiedName;
        String displayName;

        Exercise(String qualifiedName, String displayName) {
            this.qualifiedName = qualifiedName;
            this.displayName = displayName;
        }
    }
}