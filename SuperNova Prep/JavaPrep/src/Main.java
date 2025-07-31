import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    // Declare Static Final Variables
    private static final String OUTPUT_DIR = "out/production/JavaPrep";
    private static final File SRC_DIR = new File("src");

    private static final Set<String> EXCLUDED_DIRS = Set.of(".idea", "out", "bin");

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        List<Solution> solutions = discoverSolutions();

        if (solutions.isEmpty()) {
            System.out.println("No Java Files Found");
            return;
        }

        // Show Menu
        System.out.println("\n=== Java Solutions Launcher ===");
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println((i + 1) + ". " + solutions.get(i).displayName);
        }

        System.out.println("Q. Quit");
        System.out.print("Select a Solution to Run: ");

        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("q")) return;

        try {
            int choice = Integer.parseInt(input) - 1;

            if (choice >= 0 && choice < solutions.size()) {
                runSolution(solutions.get(choice));
            } else {
                System.out.println("Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Choice");
        }

        scanner.close();
    }

    // Recursively Search src/ for .java Files (Excluding Main.java)

    /**
     * Discovers all Java lesson files in the project directory
     *
     * @return List of lesson objects representing available solution programs
     */
    private static List<Solution> discoverSolutions() {
        List<Solution> solutions = new ArrayList<>();

        findJavaFiles(Main.SRC_DIR, "", solutions);

        return solutions;
    }

    /**
     * Recursively searches for Java files in the given directory and its subdirectories.
     *
     * @param dir         The directory to search in
     * @param packageName The current package name based on directory structure
     * @param solutions   The list to store found solution files
     */
    private static void findJavaFiles(File dir, String packageName, List<Solution> solutions) {
        if (EXCLUDED_DIRS.contains(dir.getName())) return;

        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                String newPackage = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();

                findJavaFiles(file, newPackage, solutions);
            } else if (file.getName().endsWith(".java") && !file.getName().equals("Main.java")) {
                String className = file.getName().replace(".java", "");
                String qualifiedName = packageName.isEmpty() ? className : packageName + "." + className;
                String displayName = className + " Program";

                solutions.add(new Solution(qualifiedName, displayName));
            }
        }
    }

    /**
     * Executes the selected solution in a new process.
     *
     * @param solution              The solution to run
     * @throws IOException          If an I/O error occurs
     * @throws InterruptedException If the process is interrupted
     */
    private static void runSolution(Solution solution) throws IOException, InterruptedException {
        clearConsole();

        Process run = new ProcessBuilder("java", "-cp", OUTPUT_DIR, solution.qualifiedName)
                .inheritIO()
                .start();

        run.waitFor();

        System.exit(0);
    }

    /**
     * Clears the console screen using platform-specific commands.
     * Falls back to printing newlines if the clear command fails.
     */
    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    /**
     * Inner class representing a tutorial lesson.
     * Contains the qualified class name and display name for the lesson.
     */
    static class Solution {
        String qualifiedName;
        String displayName;

        Solution(String qualifiedName, String displayName) {
            this.qualifiedName = qualifiedName;
            this.displayName = displayName;
        }
    }
}


