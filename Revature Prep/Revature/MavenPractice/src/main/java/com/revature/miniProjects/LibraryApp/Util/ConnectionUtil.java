package com.revature.miniProjects.LibraryApp.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String url = "jdbc:h2:./h2/db";
    private static final String username = "sa";
    private static final String password = "sa";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }
}
