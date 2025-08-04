package main.java;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Main application class that provides a menu-driven interface for running Java Multithreading lessons
 * This class discovers and executes Java files within the project structure
 */
public class Main {
    // Declare Static Final Variables
    private static final String OUTPUT_DIR = "out/production/Multithreading";
    private static final File SRC_DIR = new File("src/main/java");

    private static final Set<String> EXCLUDED_DIRS = Set.of(".idea", "out", "bin");

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        List<MultithreadingExample> examples = discoverExamples();

        if (examples.isEmpty()) {
            System.out.println("No Java Files Found");
            return;
        }

        //  Show Menu
        System.out.println("\n=== Java Multithreading Launcher ===");
        for (int i = 0; i < examples.size(); i++) {
            System.out.println((i + 1) + ". " + examples.get(i).displayName);
        }

        System.out.println("Q. Quit");;
        System.out.print("Select a Program to Run: ");

        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("q")) return;

        try {
            int choice = Integer.parseInt(input) - 1;

            if (choice >= 0 && choice < examples.size()) {
                run(examples.get(choice));
            } else {
                System.out.println("Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Choice");
        } finally {
            scanner.close();
        }
    }

    /**
     * Discovers all Java Multithreading Files in Project Directory
     * @return List of Multithreading objects representing available example programs
     */
    private static List<MultithreadingExample> discoverExamples() {
        List<MultithreadingExample> examples = new ArrayList<>();

        findJavaFiles(Main.SRC_DIR, "", examples);

        return examples;
    }

    /**
     * Recursively searches for Java files in the given directory and its subdirectories
     * @param dir The directory to search in
     * @param packageName The current package name based on directory structure
     * @param examples The list to store found example files
     */
    private static void findJavaFiles(File dir, String packageName, List<MultithreadingExample> examples) {
        if (EXCLUDED_DIRS.contains(dir.getName())) return;

        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                String newPackage = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();

                findJavaFiles(file, newPackage, examples);
            } else if (file.getName().endsWith(".java") && !file.getName().equals("Main.java")) {
                String className = file.getName().replace(".java", "");
                String qualifiedName = packageName.isEmpty() ? className : packageName + "." + className;
                String displayName = className + " Program";

                examples.add(new MultithreadingExample(qualifiedName, displayName));
            }
        }
    }

    /**
     * Executes Selected as New Process
     * @param example Multithreading Example to Run
     * @throws IOException If an I/O Error Occurs
     * @throws InterruptedException If the Process is Interrupted
     */
    private static void run(MultithreadingExample example) throws IOException, InterruptedException {
        clearConsole();

        // Run Compiled Class From IntelliJ's Output Directory
        Process runProcess = new ProcessBuilder("java", "-cp", OUTPUT_DIR, example.qualifiedName)
                .inheritIO()
                .start();

        runProcess.waitFor();

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
     * Inner class representing Multithreading
     * Contains the qualified class name and display name
     */
    static class MultithreadingExample {
        String qualifiedName;
        String displayName;

        MultithreadingExample(String qualifiedName, String displayName) {
            this.qualifiedName = qualifiedName;
            this.displayName = displayName;
        }
    }
}
