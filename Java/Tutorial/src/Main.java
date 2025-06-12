import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final File SRC_DIR = new File("src");
    private static final Set<String> EXCLUDED_DIRS = Set.of(".idea", "out", "bin");

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        List<Lesson> lessons = discoverLessons(SRC_DIR);

        if (lessons.isEmpty()) {
            System.out.println("No Java Files Found");
            return;
        }

        // Show menu
        System.out.println("\n=== Java Tutorial Launcher ===");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println((i + 1) + ". " + lessons.get(i).displayName);
        }

        System.out.println("Q. Quit");
        System.out.print("Select a Program to Run: ");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("q")) {
            return;
        }

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

        // Exit after running or error
        scanner.close();
    }

    private static List<Lesson> discoverLessons(File base) {
        List<Lesson> lessons = new ArrayList<>();
        findJavaFiles(base, "", lessons);
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
                String displayName = className + "Program"; // No package, just friendly label
                lessons.add(new Lesson(file, qualifiedName, displayName));
            }
        }
    }

    private static void runLesson(Lesson lesson) throws IOException, InterruptedException {
        clearConsole();

        // Compile
        Process compile = new ProcessBuilder("javac", lesson.file.getPath())
                .inheritIO()
                .start();
        if (compile.waitFor() != 0) {
            System.out.println("Compilation Failed");
            return;
        }

        // Run
        Process run = new ProcessBuilder("java", "-cp", "src", lesson.qualifiedName)
                .inheritIO()
                .start();
        run.waitFor();

        // Exit after running the program
        System.exit(0);
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Fallback for IDEs: print blank lines
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }


    static class Lesson {
        File file;
        String qualifiedName;
        String displayName;

        Lesson(File file, String qualifiedName, String displayName) {
            this.file = file;
            this.qualifiedName = qualifiedName;
            this.displayName = displayName;
        }
    }
}