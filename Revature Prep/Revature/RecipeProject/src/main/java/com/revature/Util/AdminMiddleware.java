package com.revature.Util;

import com.revature.DAO.ChefDAO;
import com.revature.Model.Chef;
import com.revature.Service.AuthenticationService;
import com.revature.Service.ChefService;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;

import java.util.stream.Collectors;

public class AdminMiddleware implements Handler {
    private String[] protectedMethods;
    private AuthenticationService authenticationService;

    public AdminMiddleware(String... protectedMethods) {
        this.protectedMethods = protectedMethods;
        this.authenticationService = new AuthenticationService(new ChefService(new ChefDAO(new ConnectionUtil())));
    }

    @Override
    public void handle(Context context) {
        if (isProtectedMethod(context.method().name())) {
            String token = authenticationService.loggedInChefs.keySet().stream().collect(Collectors.joining());
            boolean isAdmin = isAdmin(authenticationService.getChefFromSessionToken(token));

            if (!isAdmin) {
                throw new UnauthorizedResponse("Access Denied");
            }
        }
    }

    private boolean isProtectedMethod(String method) {
        for (String protectedMethod : protectedMethods) {
            if (protectedMethod.toString().equalsIgnoreCase(method)) {
                return true;
            }
        }

        return false;
    }

    private boolean isAdmin(Chef chef) {
        if (chef != null) {
            return chef.isAdmin();
        }

        return false;
    }
}
