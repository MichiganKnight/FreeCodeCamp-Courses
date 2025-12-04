package com.revature.miniProjects.LibraryApp.DAO;

import com.revature.miniProjects.LibraryApp.Model.Book;
import com.revature.miniProjects.LibraryApp.Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public List<Book> getAllBooks() {
        Connection connection = ConnectionUtil.getConnection();
        List<Book> books = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Book";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("isbn"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getInt("copies_available")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return books;
    }

    public Book getBookByIsbn(int isbn) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Book WHERE isbn = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, isbn);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("isbn"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getInt("copies_available")
                );
                return book;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return null;
    }

    public Book insertBook(Book book) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO Book VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, book.getIsbn());
            preparedStatement.setInt(2, book.getAuthor_id());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setInt(4, book.getCopies_available());

            preparedStatement.executeUpdate();

            return book;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return null;
    }

    public List<Book> getBooksWithBookCountOverZero() {
        Connection connection = ConnectionUtil.getConnection();
        List<Book> books = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Book WHERE copies_available > 0";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Book book = new Book(rs.getInt("isbn"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getInt("copies_available"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return books;
    }
}
