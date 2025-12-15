package com.revature.weekNine.beanLifecycle;

import java.sql.*;

public class StudentDAO {
    static String driver;
    static Connection conn;
    static String url;
    static String username;
    static String password;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        System.out.println("Setting Driver");
        StudentDAO.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        System.out.println("Setting URL");
        StudentDAO.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        System.out.println("Setting Username");
        StudentDAO.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        System.out.println("Setting Password");
        StudentDAO.password = password;
    }

    void init() throws ClassNotFoundException, SQLException {
        System.out.println("Initializing Connection");
        createConnection();
    }

    static void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conn = DriverManager.getConnection(url, username, password);
    }

    static void closeConnection() throws SQLException {
        conn.close();
    }

    void getAllRecords() throws SQLException {
        String sql = "SELECT * FROM student";
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) +  " " + rs.getString(4));
        }
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("Initializing Connection");
        createConnection();
    }

    public void destroy() throws SQLException {
        System.out.println("Closing Connection");
        closeConnection();
    }
}
