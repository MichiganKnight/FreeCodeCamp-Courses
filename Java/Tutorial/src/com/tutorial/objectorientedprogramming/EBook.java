package com.tutorial.objectorientedprogramming;

public class EBook extends Book {
    private String format;

    EBook(String title, String author, int pageCount, String format) {
        // Parent Class
        super(title, author, 0);

        this.format = format;
    }
}
