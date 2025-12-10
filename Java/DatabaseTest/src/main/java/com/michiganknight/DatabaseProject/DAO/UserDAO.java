package com.michiganknight.DatabaseProject.DAO;

import com.michiganknight.DatabaseProject.Model.User;
import com.michiganknight.DatabaseProject.Util.ConnectionUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public void createUserTable() {
        Connection connection = ConnectionUtil.getConnection();

        try {
            Statement statement = connection.createStatement();
            statement.execute("USE DatabaseProjectDB");

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(connection.getCatalog(), null, "Users", null);

            if (rs.next()) {
                System.out.println("Table 'Users' Already Exists");
            } else {
                String sqlFilePath = "src/main/java/com/michiganknight/DatabaseProject/SQL/CreateTable.sql";
                String sql = new String(Files.readAllBytes(Paths.get(sqlFilePath)));
                statement.execute(sql);

                System.out.println("Table Created Successfully");
            }
        } catch (SQLException | IOException ex) {
            System.err.println("An Error Occurred: " + ex.getMessage());
        }
    }

    public void createUser(User user) {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO Users (firstName, lastName, email, username, password, dateCreated) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Statement statement = connection.createStatement();
            statement.execute("USE DatabaseProjectDB");

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setDate(6, new Date(System.currentTimeMillis()));

            preparedStatement.executeUpdate();
        }  catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        }
    }

    public List<User> getAllUsers() {
        Connection connection = ConnectionUtil.getConnection();
        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            statement.execute("USE DatabaseProjectDB");

            String sql = "SELECT * FROM Users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User(resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"), resultSet.getString("username"), resultSet.getString("password"));
                users.add(user);
            }
        }  catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        }

        return users;
    }
}
