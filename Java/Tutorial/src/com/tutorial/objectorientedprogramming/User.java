package com.tutorial.objectorientedprogramming;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class User {
    private String name;
    private LocalDate birthDate;

    private ArrayList<Book> books = new ArrayList<Book>();

    //public User() {}

    public User(String name, String birthDate) {
        this.name = name;
        this.birthDate = LocalDate.parse(birthDate);
    }

    public int age() {
        // this - Current Object Being Worked On
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    public void borrow(Book book) {
        this.books.add(book);
    }

    public String getName() {
        return this.name;
    }

    public String getBirthDate() {
        return this.birthDate.toString();
    }

    public String borrowedBooks() {
        return this.books.toString();
    }
}
