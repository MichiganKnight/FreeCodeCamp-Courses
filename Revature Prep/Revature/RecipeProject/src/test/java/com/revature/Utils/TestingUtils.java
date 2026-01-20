package com.revature.Utils;

import com.revature.Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestingUtils {
    private static Connection connection;

    public static void assertCountDifference(int expectedDifference, String message, String countSelStatement, Runnable exec) {
        int before = count(countSelStatement);
        exec.run();
        int after = count(countSelStatement);

        if (after - before != expectedDifference) {
            throw new RuntimeException(message);
        }
    }

    private static int count(String countSelStatement) {
        try {
            Connection connection = new ConnectionUtil().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(countSelStatement);
            resultSet.next();

            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to Count Ingredients");
        }
    }
}
