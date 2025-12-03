package com.revature.weekSix.user;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDAO {
    private final List<User> users = Arrays.asList(
            new User(0, "Steve Rogers"),
            new User(1, "Tony Stark"),
            new User(3, "Carol Danvers")
    );

    private static UserDAO userDAO = null;

    private UserDAO() {}

    static UserDAO instance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }

        return userDAO;
    }

    Optional<User> getUserById(int id) {
        return users.stream()
                .filter(user -> user.id==id)
                .findAny();
    }

    Iterable<String> getAllUsers() {
        return users.stream()
                .map(user -> user.name)
                .collect(Collectors.toList());
    }
}
