package com.revature.miniProjects.LibraryApp.Service;

import com.revature.miniProjects.LibraryApp.DAO.BookDAO;
import com.revature.miniProjects.LibraryApp.Model.Book;

import java.util.List;

public class BookService {
    private BookDAO bookDAO;

    public BookService() {
        bookDAO = new BookDAO();
    }

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public Book addBook(Book book) {
        if (book.getIsbn() == 0) {
            return null;
        }

        Book existingBook = bookDAO.getBookByIsbn(book.getIsbn());

        if (existingBook != null) {
            return null;
        } else {
            return bookDAO.insertBook(book);
        }
    }

    public List<Book> getAllAvailableBooks() {
        return bookDAO.getBooksWithBookCountOverZero();
    }
}
