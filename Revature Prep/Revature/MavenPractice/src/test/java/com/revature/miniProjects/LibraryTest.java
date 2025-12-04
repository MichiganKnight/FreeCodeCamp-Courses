package com.revature.miniProjects;

import com.revature.miniProjects.LibraryApp.Application;
import com.revature.miniProjects.LibraryApp.DAO.AuthorDAO;
import com.revature.miniProjects.LibraryApp.DAO.BookDAO;
import com.revature.miniProjects.LibraryApp.Model.Author;
import com.revature.miniProjects.LibraryApp.Model.Book;
import com.revature.miniProjects.LibraryApp.Service.AuthorService;
import com.revature.miniProjects.LibraryApp.Service.BookService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class LibraryTest {
    public AuthorDAO authorDAO;
    public AuthorService authorService;
    public AuthorDAO mockAuthorDAO;
    public BookDAO bookDAO;
    public BookService bookService;
    public BookDAO mockBookDAO;

    @BeforeEach
    public void setUp() {
        Application.databaseSetup();
        authorDAO = new AuthorDAO();
        mockAuthorDAO = Mockito.mock(AuthorDAO.class);
        authorService = new AuthorService();
        bookDAO = new BookDAO();
        mockBookDAO = Mockito.mock(BookDAO.class);
        bookService = new BookService();
    }

    @Test
    public void AuthorDAO_GetAllAuthorsTest() {
        List<Author> authors = authorDAO.getAllAuthors();
        Author a1 = new Author(1, "jorge luis borges");
        Author a2 = new Author(2, "italo calvino");
        Author a3 = new Author(3, "thomas pynchon");
        Author a4 = new Author(4, "marshall mcluhan");
        Author a5 = new Author(5, "immanuel kant");

        Assertions.assertTrue(authors.contains(a1));
        Assertions.assertTrue(authors.contains(a2));
        Assertions.assertTrue(authors.contains(a3));
        Assertions.assertTrue(authors.contains(a4));
        Assertions.assertTrue(authors.contains(a5));
    }

    @Test
    public void AuthorDAO_InsertAuthorCheckWithGetAllTest() {
        Author a6 = new Author(6, "james joyce");
        authorDAO.insertAuthor(a6);

        List<Author> authors = authorDAO.getAllAuthors();
        Assertions.assertTrue(authors.contains(a6));
    }

    @Test
    public void AuthorService_GetAllAuthorsServiceTest() {
        List<Author> authors = new ArrayList<>();
        Author a1 = new Author(1, "James Joyce");
        Author a2 = new Author(2, "Leo Tolstoy");
        authors.add(a1);
        authors.add(a2);

        Mockito.when(mockAuthorDAO.getAllAuthors()).thenReturn(authors);
        List<Author> returnedAuthors = authorService.getAllAuthors();

        if (returnedAuthors == null) {
            Assertions.fail();
        } else {
            Assertions.assertTrue(returnedAuthors.contains(a1));
            Assertions.assertTrue(returnedAuthors.contains(a2));
        }
    }

    @Test
    public void AuthorService_AddAuthorTest() {
        Author newAuthor = new Author("James Joyce");
        Author persistedAuthor = new Author(1, "James Joyce");
        Mockito.when(mockAuthorDAO.insertAuthor(newAuthor)).thenReturn(persistedAuthor);
        Author actualAuthor = authorService.addAuthor(newAuthor);

        Assertions.assertEquals(persistedAuthor, actualAuthor);
    }

    @Test
    public void GetAllBooksTest() {
        List<Book> books = bookDAO.getAllBooks();
    }

    @Test
    public void BookDAO_GetBookByIsbnTest() {
        Book b1 = new Book(100, 1, "ficciones", 2);
        Book book = bookDAO.getBookByIsbn(100);

        Assertions.assertEquals(b1, book);
    }

    @Test
    public void BookDAO_InsertBookCheckWithGetAllTest() {
        Book b1 = new Book(108, 1, "cosmicomics", 1);
        bookDAO.insertBook(b1);
        List<Book> books = bookDAO.getAllBooks();

        Assertions.assertTrue(books.contains(b1));
    }

    @Test
    public void BookDAO_InsertBookCheckWithGetByIdTest() {
        Book b1 = new Book(108, 1, "cosmicomics", 1);
        bookDAO.insertBook(b1);
        Book book = bookDAO.getBookByIsbn(108);

        Assertions.assertEquals(b1, book);
    }

    @Test
    public void BookDAO_GetBooksWithBookCountOverZeroTest() {
        Book b0 = new Book(100, 1, "ficciones", 2);
        Book b1 = new Book(102, 2, "mr palomar", 1);
        Book b2 = new Book(103, 2, "invisible cities", 3);
        Book b3 = new Book(106, 4, "understanding media", 1);
        Book b4 = new Book(107, 5, "critique of pure reason", 7);
        List<Book> availableBooks = bookDAO.getBooksWithBookCountOverZero();

        Assertions.assertTrue(availableBooks.contains(b0));
        Assertions.assertTrue(availableBooks.contains(b1));
        Assertions.assertTrue(availableBooks.contains(b2));
        Assertions.assertTrue(availableBooks.contains(b3));
        Assertions.assertTrue(availableBooks.contains(b4));
        Assertions.assertTrue(availableBooks.size() == 5);
    }

    @Test
    public void BookService_GetAllBooksTest1() {
        List<Book> bookList = new ArrayList<Book>();
        Book b1 = new Book(101, 1, "Ulysses", 2);
        Book b2 = new Book(102, 1, "Finnegan's Wake", 0);
        Book b3 = new Book(103, 2, "War and Peace", 1);
        bookList.add(b1);
        bookList.add(b2);
        bookList.add(b3);

        Mockito.when(mockBookDAO.getAllBooks()).thenReturn(bookList);
        List<Book> returnedBooks = bookService.getAllBooks();

        if (returnedBooks == null) {
            Assertions.fail();
        } else {
            Assertions.assertTrue(returnedBooks.contains(b1));
            Assertions.assertTrue(returnedBooks.contains(b2));
            Assertions.assertTrue(returnedBooks.contains(b3));
        }
    }

    @Test
    public void BookService_AddBookVerifyNotNullOnSuccessfulAddTest() {
        List<Book> bookList = new ArrayList<Book>();
        Book b1 = new Book(101, 1, "Ulysses", 2);
        Book b2 = new Book(102, 1, "Finnegan's Wake", 0);
        Book b3 = new Book(103, 2, "War and Peace", 1);
        Book b4 = new Book(104, 3, "Roadside Picnic", 1);
        bookList.add(b1);
        bookList.add(b2);
        bookList.add(b3);

        Mockito.when(mockBookDAO.getAllBooks()).thenReturn(bookList);
        Mockito.when(mockBookDAO.getBookByIsbn(104)).thenReturn(null);
        Mockito.when(mockBookDAO.insertBook(b4)).thenReturn(b4);

        Assertions.assertEquals(b4, bookService.addBook(b4));
        Mockito.verify(mockBookDAO).insertBook(b4);
    }

    @Test
    public void BookService_AddBookVerifNullOnUnsuccessfulAddTest() {
        List<Book> bookList = new ArrayList<Book>();
        Book b1 = new Book(101, 1, "Ulysses", 2);
        Book b2 = new Book(102, 1, "Finnegan's Wake", 0);
        Book b3 = new Book(103, 2, "War and Peace", 1);
        bookList.add(b1);
        bookList.add(b2);
        bookList.add(b3);

        Mockito.when(mockBookDAO.getAllBooks()).thenReturn(bookList);
        Mockito.when(mockBookDAO.getBookByIsbn(103)).thenReturn(b3);

        Assertions.assertEquals(null, bookService.addBook(b3));
        Mockito.verify(mockBookDAO, Mockito.never()).insertBook(b3);
    }

    @Test
    public void SetBookService_GetAllAvailableBooksTest() {
        List<Book> bookList = new ArrayList<>();
        List<Book> bookListOverZero = new ArrayList<>();
        Book b1 = new Book(101, 1, "Ulysses", 2);
        Book b2 = new Book(102, 1, "Finnegan's Wake", 0);
        Book b3 = new Book(103, 2, "War and Peace", 1);
        bookList.add(b1);
        bookList.add(b2);
        bookList.add(b3);
        bookListOverZero.add(b1);
        bookListOverZero.add(b3);

        Mockito.when(mockBookDAO.getAllBooks()).thenReturn(bookList);
        Mockito.when(mockBookDAO.getBooksWithBookCountOverZero()).thenReturn(bookListOverZero);
        List<Book> returnedBooks = bookService.getAllAvailableBooks();
        
        if (returnedBooks == null) {
            Assertions.fail();
        } else {
            Assertions.assertTrue(returnedBooks.contains(b1));
            Assertions.assertTrue(returnedBooks.contains(b3));
            Assertions.assertFalse(returnedBooks.contains(b2));
        }
    }
}
