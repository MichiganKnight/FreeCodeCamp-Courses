package com.revature.weekThree.git;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GitCommands {
    public static void main(String[] args) {
        System.out.println("=== Common GitBash / Linux Commands ===");

        String path = System.getProperty("user.dir");
        String weekThreePath = Paths.get(path, "src", "com", "revature", "weekThree").toString();

        if (Files.notExists(Paths.get(weekThreePath))) {
            System.out.println("No Directory Found");
        } else {
            //runLinuxCommand("ls", "-l");
            System.out.println("ls    | Lists Directory Contents");
            System.out.println("ls -l | Lists Detailed Directory Contents");
            System.out.println("cd    | Changes the Current Directory - Uses / (Forward Slash)");
            System.out.println("pwd   | Identifies Working Directory");
            System.out.println("mkdir | Makes a Directory");
            System.out.println("cat   | Create / View / Concatenate Files");
            System.out.println("touch | Creates an Empty File");
            System.out.println("echo  | Prints Strings Passed as Arguments");
            System.out.println("grep  | Search Files and Directories for a String");
            System.out.println("diff  | Compares two Files Line by Line to Find Differences");
            System.out.println("mv    | Moves or Renames a File");
            System.out.println("mv -i | Moves or Renames a File - Will Prompt if Overwriting Existing File");
            System.out.println("mv -n | Moves or Renames a File - Will Not Move if Overwriting Existing File");
            System.out.println("mv -u | Moves or Renames a File - Only Moves if Source File is Newer than Destination File");
            System.out.println("mv -u | Moves or Renames a File - Creates a Backup of an Existing Destination File Being Overwritten");
        }
    }

    private static void runLinuxCommand(String command, String args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command, args);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited With Code: " + exitCode);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
