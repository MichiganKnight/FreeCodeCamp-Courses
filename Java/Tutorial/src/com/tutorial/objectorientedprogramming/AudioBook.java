package com.tutorial.objectorientedprogramming;

public class AudioBook extends Book {
    private int runTime;

    AudioBook(String title, String author, int runTime) {
        // Parent Class
        super(title, author, 0);

        this.runTime = runTime;
    }
}
