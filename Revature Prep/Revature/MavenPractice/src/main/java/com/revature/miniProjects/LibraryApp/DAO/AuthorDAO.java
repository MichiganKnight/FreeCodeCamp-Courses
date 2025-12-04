package com.revature.miniProjects.LibraryApp.DAO;

import com.revature.miniProjects.LibraryApp.Model.Author;
import com.revature.miniProjects.LibraryApp.Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
    public List<Author> getAllAuthors() {
        Connection connection = ConnectionUtil.getConnection();
        List<Author> authors = new ArrayList<>();

        try {
            String sql = "SELECT * FROM AUTHOR";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Author author = new Author(rs.getInt("id"), rs.getString("name"));
                authors.add(author);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return authors;
    }

    public Author insertAuthor(Author author) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO AUTHOR VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, author.getId());
            preparedStatement.setString(2, author.getName());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int generated_author_id = (int) rs.getLong(1);
                return new Author(generated_author_id, author.getName());
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return null;
    }
}
