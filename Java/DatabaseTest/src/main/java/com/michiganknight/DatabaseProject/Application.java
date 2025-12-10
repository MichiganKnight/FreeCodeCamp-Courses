package com.michiganknight.DatabaseProject;

import com.michiganknight.DatabaseProject.Model.User;
import com.michiganknight.DatabaseProject.Service.UserService;
import com.michiganknight.DatabaseProject.Util.ConnectionUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        databaseSetup();

        UserService userService = new UserService();
        userService.createUserTable();

        //userService.createUser(new User("Drew", "Fleming", "drewfleming@example.com", "drewfleming", "password"));

        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user.toString());
        }
    }

    private static void databaseSetup() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            String dbName = "DatabaseProjectDB";

            statement.execute("USE master");

            String checkDbSql = String.format("SELECT name FROM sys.databases WHERE name = '%s'", dbName);

            try {
                ResultSet rs = statement.executeQuery(checkDbSql);

                if (rs.next()) {
                    System.out.println("Database '" + dbName + "' Already Exists");

                    statement.execute("USE " + dbName);
                }
                else {
                    String sqlFilePath = "src/main/java/com/michiganknight/DatabaseProject/SQL/CreateDatabase.sql";
                    String sql = new String(Files.readAllBytes(Paths.get(sqlFilePath)));

                    statement.execute(sql);

                    System.out.println("Database Created Successfully");
                }
            } catch (SQLException ex) {
                System.err.println("SQL Exception: " + ex.getMessage());
            }
        } catch (SQLException | IOException ex) {
            System.err.println("An Error Occurred: " + ex.getMessage());
        }
    }
}