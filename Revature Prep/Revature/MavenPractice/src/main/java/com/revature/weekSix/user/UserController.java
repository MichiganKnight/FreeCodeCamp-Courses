package com.revature.weekSix.user;

import io.javalin.http.Handler;

import java.util.Objects;
import java.util.Optional;

public class UserController {
    public static Handler fetchAllUsers = ctx -> {
        UserDAO dao = UserDAO.instance();
        Iterable<String> allUsers =  dao.getAllUsers();
        ctx.json(allUsers);
    };

    public static Handler fetchById = ctx -> {
        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
        UserDAO dao = UserDAO.instance();
        Optional<User> user = dao.getUserById(id);

        if (user.isPresent()) {
            ctx.json(user.get());
        } else {
            ctx.html("Not Found");
        }
    };
}
