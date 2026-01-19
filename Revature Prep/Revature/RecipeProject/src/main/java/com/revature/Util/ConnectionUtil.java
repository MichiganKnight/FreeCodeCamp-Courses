package com.revature.Util;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtil {
    private static String url = "jdbc:h2:./h2/db;";
    private static String user = "sa";
    private static String password = "";
    private static JdbcDataSource pool = new JdbcDataSource();

    static {
        pool.setURL(url);
        pool.setUser(user);
        pool.setPassword(password);
    }

    public Connection getConnection() {
        try {
            return pool.getConnection();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return null;
    }
}
