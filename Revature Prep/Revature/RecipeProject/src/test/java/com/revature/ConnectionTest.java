package com.revature;

import com.revature.Util.ConnectionUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnectionTest {
    @Test
    public void getConnectionTest() throws SQLException {
        Connection connection = new ConnectionUtil().getConnection();
        assertNotNull(connection, () -> "Connecting Should Not Be Null");
        connection.close();
    }
}
