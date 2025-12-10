package com.michiganknight.DatabaseProject.Service;

import com.michiganknight.DatabaseProject.DAO.UserDAO;
import com.michiganknight.DatabaseProject.Model.User;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void createUserTable() {
        userDAO.createUserTable();
    }

    public void createUser(User user) {
        userDAO.createUser(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
