package com.revature.Util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class DBUtil {
    private static StringBuilder sqlScript = new StringBuilder();
    private static InputStream inputStream = DBUtil.class.getResourceAsStream("/Script.sql");

    static {
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (!nextLine.startsWith("--")) {
                sqlScript.append(nextLine).append(" ");
            }
        }

        scanner.close();
    }

    public static void RUN_SQL() {
        try (Connection connection = new ConnectionUtil().getConnection()) {
            connection.prepareStatement("DROP ALL OBJECTS").executeUpdate();
            connection.prepareStatement(sqlScript.toString()).executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}
