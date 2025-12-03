package com.revature.weekThree.sql;/*package com.revature.weekThree.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLTable {
    public static void setTable(Connection connection, String databaseName) {
        try {
            Statement statement = connection.createStatement();
            String sql = "USE " + databaseName + ";";

            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void createTable(Connection connection, String tableName, ArrayList<String> sqlFields) {
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE " + tableName.toLowerCase().trim() + " ("
                    + "ID INTEGER PRIMARY KEY NOT NULL, "
                    + sqlFields.getFirst().toLowerCase().trim() + " VARCHAR(50) NOT NULL, "
                    + sqlFields.get(1).toLowerCase().trim() + " VARCHAR(50) NOT NULL, "
                    + "Created_At DATETIME NOT NULL)";
            statement.executeUpdate(sql);

            System.out.println("Database: " + tableName + " Created");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
}*/