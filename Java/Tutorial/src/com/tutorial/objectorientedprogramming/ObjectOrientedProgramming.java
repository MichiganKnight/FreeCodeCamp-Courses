package com.tutorial.objectorientedprogramming;

import java.time.LocalDate;

public class ObjectOrientedProgramming {
    public static void main(String[] args) {
        System.out.println("=== Object Oriented Programming ===");

        User youngerUser = new User("Drew Person", "1999-03-01");
        //youngerUser.name = "Drew Person";
        //youngerUser.birthDate = LocalDate.parse("1999-03-01");

        System.out.printf("%s Was Born On: %s%n", youngerUser.getName(), youngerUser.getBirthDate());
        System.out.printf("You are Now: %d Years Old!%n", youngerUser.age());

        User olderUser = new User("Older Drew", "1776-07-04");

        System.out.printf("%s Was Born On: %s%n", olderUser.getName(), olderUser.getBirthDate());
        System.out.printf("You are Now: %d Years Old!%n", olderUser.age());

        Book harryPotter = new Book("Harry Potter And The Philosopher's Stone", "JK Rowling", 270);
        AudioBook dracula = new AudioBook("Dracula", "Bram Stoker",30000);
        EBook jeeves = new EBook("Carry on Jeeves", "P.G. Wodehouse", 280, "PDF");

        youngerUser.borrow(harryPotter);
        System.out.printf("%s Has Borrowed These books: %s%n", youngerUser.getName(), youngerUser.borrowedBooks());

        System.out.println(dracula.toString());
        System.out.println(jeeves.toString());
    }
}
