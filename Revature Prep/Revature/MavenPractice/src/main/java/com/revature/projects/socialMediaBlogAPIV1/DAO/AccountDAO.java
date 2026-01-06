package com.revature.projects.socialMediaBlogAPIV1.DAO;

import com.revature.projects.Util.ConnectionUtil;
import com.revature.projects.socialMediaBlogAPIV1.Model.Account;

import java.sql.*;

public class AccountDAO {
    public Account createAccount(Account account) {
        if (account.getUsername() != null && !account.getUsername().isBlank() && account.getPassword().length() >= 4) {
            Connection connection = ConnectionUtil.getConnection();

            try {
                String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());

                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int generated_account_id = (int) rs.getLong(1);
                    return new Account(
                            generated_account_id,
                            account.getUsername(),
                            account.getPassword()
                    );
                }
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid Username or Password");
            return null;
        }

        return null;
    }

    public Account getAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT account_id, username, password FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return null;
    }

    public Account getAccountById(int id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT account_id, username, password FROM account WHERE account_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Account(
                        resultSet.getInt("account_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return null;
    }
}
