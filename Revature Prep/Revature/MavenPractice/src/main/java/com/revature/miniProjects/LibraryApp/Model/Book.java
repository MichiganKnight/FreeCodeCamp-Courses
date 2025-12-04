package com.revature.miniProjects.LibraryApp.Model;

import java.util.Objects;

public class Book {
    int isbn;
    int author_id;
    String title;
    int copies_available;

    public Book() {}

    public Book(int isbn, int author_id, String title, int copies_available) {
        this.isbn = isbn;
        this.author_id = author_id;
        this.title = title;
        this.copies_available = copies_available;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCopies_available() {
        return copies_available;
    }

    public void setCopies_available(int copies_available) {
        this.copies_available = copies_available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return isbn == book.isbn && author_id == book.author_id && title.equals(book.title) && copies_available == book.copies_available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", author_id=" + author_id +
                ", title='" + title + '\'' +
                ", copies_available=" + copies_available +
                '}';
    }
}
