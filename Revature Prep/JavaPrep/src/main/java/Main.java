package main.java;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        URL htmlResource = Main.class.getClassLoader().getResource("StudyGuide.html");

        if (htmlResource == null) {
            System.out.println("Error: StudyGuide.html Not Found");

            return;
        }

        try {
            File htmlFile = Paths.get(htmlResource.toURI()).toFile();
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error Opening File: " + e.getMessage());
        }
    }
}
