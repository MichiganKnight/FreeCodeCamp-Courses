package com.revature.Controller;

import com.revature.Model.Chef;
import com.revature.Service.AuthenticationService;
import com.revature.Service.ChefService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.Map;

public class AuthenticationController {
    private ChefService chefService;
    private AuthenticationService authenticationService;

    public AuthenticationController(ChefService chefService, AuthenticationService authenticationService) {
        this.chefService = chefService;
        this.authenticationService = authenticationService;
    }

    public void register(Context context) {
        Chef newChef = context.bodyAsClass(Chef.class);

        if (chefService.searchChefs(newChef.getUsername()).stream().anyMatch(chef -> chef.getUsername().equals(newChef.getUsername()))) {
            context.status(409).result("Username Already Exists");

            return;
        }

        Chef registeredChef = authenticationService.registerChef(newChef);
        context.status(201).json(registeredChef);
    }

    public void login(Context context) {
        Chef chef = context.bodyAsClass(Chef.class);
        String token = authenticationService.login(chef);

        if (token != null) {
            boolean isAdmin = chef.getUsername().equalsIgnoreCase("ChefTrevin");

            String accept = context.header("Accept");
            if (accept == null) {
                accept = "text/plain";
            }

            if (accept.contains("application/json")) {
                context.json(Map.of("token", token, "isAdmin", isAdmin));
            } else {
                context.result(token + " " + isAdmin);
            }
        } else {
            context.status(401).result("Invalid Username or Password");
        }
    }

    public void logout(Context context) {
        authenticationService.logout(context.header("Authorization").split(" ")[1]);

        if (" " != null) {
            context.status(200).result("Logout Successful");
        }
    }

    public void configureRoutes(Javalin app) {
        app.post("/register", this::register);
        app.post("/login", this::login);
        app.post("/logout", this::logout);
    }
}
