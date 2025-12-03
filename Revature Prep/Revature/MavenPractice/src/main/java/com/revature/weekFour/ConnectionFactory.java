package com.revature.weekFour;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final Properties properties = new Properties();
    private String connectionUrl;

    private ConnectionFactory() {
        try {
            properties.load(new FileReader("src/main/resources/db.properties"));

            this.connectionUrl = properties.getProperty("url");

            properties.remove("url");
        } catch (IOException ex) {
            System.err.println("Error Loading Properties File: " + ex.getMessage());
        }
    }

    public static ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(connectionUrl, properties);
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());

            return null;
        }
    }
}
