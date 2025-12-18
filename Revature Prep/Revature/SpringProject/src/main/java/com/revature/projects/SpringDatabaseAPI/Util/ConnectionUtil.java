package com.revature.projects.SpringDatabaseAPI.Util;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String URL = "jdbc:h2:./h2/db";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "sa";
    private static final JdbcDataSource POOL = new JdbcDataSource();

    static {
        POOL.setURL(URL);
        POOL.setUser(USERNAME);
        POOL.setPassword(PASSWORD);
    }

    public static Connection getConnection() {
        try {
            return POOL.getConnection();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return null;
    }

    public static void resetTestDatabase() {
        try {
            FileReader sqlReader = new FileReader("src/main/resources/SpringDatabase.sql");
            RunScript.execute(getConnection(), sqlReader);
        } catch (SQLException | FileNotFoundException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
