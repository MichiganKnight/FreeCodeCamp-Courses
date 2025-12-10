package com.michiganknight.DatabaseProject.Util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Properties properties = new Properties();

    public static Connection getConnection() {
        try {
            properties.load(new FileReader("src/main/resources/db.properties"));

            String connectionUrl = properties.getProperty("url");

            return DriverManager.getConnection(connectionUrl, properties);
        } catch (SQLException | IOException ex) {
            System.err.println("An Error Occurred: " + ex.getMessage());

            return null;
        }
    }
}
