package main.java;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String htmlPath = System.getProperty("user.dir") + "/src/main/StudyGuide.html";

        File htmlFile = new File(htmlPath);

        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            System.out.println("Error Opening File: " + e.getMessage());
        }
    }
}
