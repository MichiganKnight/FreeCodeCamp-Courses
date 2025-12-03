package com.revature.weekSix;

import com.revature.weekSix.user.User;
import com.revature.weekSix.user.UserController;
import io.javalin.Javalin;

public class TestAPI {
    public static void main(String[] args) {
        System.out.println("=== Test API Using Javalin ===");

        Javalin app = Javalin.create().start(7000);

        app.get("/Users", UserController.fetchAllUsers);
        app.get("/Users/{id}", UserController.fetchById);

        app.post("/",  ctx -> {
            User user = ctx.bodyAsClass(User.class);
        });
    }
}
