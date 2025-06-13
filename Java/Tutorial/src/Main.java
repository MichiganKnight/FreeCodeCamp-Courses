import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    // Declare Static Final Variables
    private static final String OUTPUT_DIR = "out/production/Tutorial";
    private static final File SRC_DIR = new File("src");

    private static final Set<String> EXCLUDED_DIRS = Set.of(".idea", "out", "bin");

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        List<Lesson> lessons = discoverLessons();

        if (lessons.isEmpty()) {
            System.out.println("No Java Files Found");
            return;
        }

        // Show Menu
        System.out.println("\n=== Java Tutorial Launcher ===");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println((i + 1) + ". " + lessons.get(i).displayName);
        }

        System.out.println("Q. Quit");
        System.out.print("Select a Program to Run: ");

        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("q")) return;

        try {
            int choice = Integer.parseInt(input) - 1;

            if (choice >= 0 && choice < lessons.size()) {
                runLesson(lessons.get(choice));
            } else {
                System.out.println("Invalid Choice");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Choice");
        }

        scanner.close();
    }

    // Recursively Search src/ for .java Files (Excluding Main.java)
    private static List<Lesson> discoverLessons() {
        List<Lesson> lessons = new ArrayList<>();

        findJavaFiles(Main.SRC_DIR, "", lessons);

        return lessons;
    }

    private static void findJavaFiles(File dir, String packageName, List<Lesson> lessons) {
        if (EXCLUDED_DIRS.contains(dir.getName())) return;

        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                String newPackage = packageName.isEmpty() ? file.getName() : packageName + "." + file.getName();

                findJavaFiles(file, newPackage, lessons);
            } else if (file.getName().endsWith(".java") && !file.getName().equals("Main.java")) {
                String className = file.getName().replace(".java", "");
                String qualifiedName = packageName.isEmpty() ? className : packageName + "." + className;
                String displayName = className + " Program"; // Friendly Display Name

                lessons.add(new Lesson(qualifiedName, displayName));
            }
        }
    }

    private static void runLesson(Lesson lesson) throws IOException, InterruptedException {
        clearConsole();

        // Run Compiled Class From IntelliJ's Output Directory
        Process run = new ProcessBuilder("java", "-cp", OUTPUT_DIR, lesson.qualifiedName)
                .inheritIO()
                .start();

        run.waitFor();

        System.exit(0); // Exit
    }

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

    static class Lesson {
        String qualifiedName;
        String displayName;

        Lesson(String qualifiedName, String displayName) {
            this.qualifiedName = qualifiedName;
            this.displayName = displayName;
        }
    }
}