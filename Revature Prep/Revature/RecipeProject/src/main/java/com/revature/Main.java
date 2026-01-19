package com.revature;

import com.revature.Controller.AuthenticationController;
import com.revature.Controller.IngredientController;
import com.revature.Controller.RecipeController;
import com.revature.DAO.ChefDAO;
import com.revature.DAO.IngredientDAO;
import com.revature.DAO.RecipeDAO;
import com.revature.Service.AuthenticationService;
import com.revature.Service.ChefService;
import com.revature.Service.IngredientService;
import com.revature.Service.RecipeService;
import com.revature.Util.ConnectionUtil;
import com.revature.Util.DBUtil;
import com.revature.Util.JavalinAppUtil;
import io.javalin.Javalin;

public class Main {
    private static final ConnectionUtil CONNECTION_UTIL = new ConnectionUtil();
    private static JavalinAppUtil JAVALIN_APP_UTIL;
    private static AuthenticationController AUTHENTICATION_CONTROLLER;
    private static IngredientController INGREDIENT_CONTROLLER;
    private static RecipeController RECIPE_CONTROLLER;
    private static AuthenticationService AUTHENTICATION_SERVICE;
    private static ChefService CHEF_SERVICE;
    private static IngredientService INGREDIENT_SERVICE;
    private static RecipeService RECIPE_SERVICE;
    private static ChefDAO CHEF_DAO;
    private static IngredientDAO INGREDIENT_DAO;
    private static RecipeDAO RECIPE_DAO;

    public static void main(String[] args) {
        startServer(8081, true);
    }

    public static Javalin startServer(int preferredPort, boolean allowFallback) {
        CHEF_DAO = new ChefDAO(CONNECTION_UTIL);
        INGREDIENT_DAO = new IngredientDAO(CONNECTION_UTIL);
        RECIPE_DAO = new RecipeDAO(CHEF_DAO, INGREDIENT_DAO, CONNECTION_UTIL);
        CHEF_SERVICE = new ChefService(CHEF_DAO);
        AUTHENTICATION_SERVICE = new AuthenticationService(CHEF_SERVICE);
        RECIPE_SERVICE = new RecipeService(RECIPE_DAO);
        INGREDIENT_SERVICE = new IngredientService(INGREDIENT_DAO);
        AUTHENTICATION_CONTROLLER = new AuthenticationController(CHEF_SERVICE, AUTHENTICATION_SERVICE);
        INGREDIENT_CONTROLLER = new IngredientController(INGREDIENT_SERVICE);
        RECIPE_CONTROLLER = new RecipeController(RECIPE_SERVICE, AUTHENTICATION_SERVICE);
        JAVALIN_APP_UTIL = new JavalinAppUtil(RECIPE_CONTROLLER, AUTHENTICATION_CONTROLLER, INGREDIENT_CONTROLLER);

        DBUtil.RUN_SQL();

        Javalin app = JAVALIN_APP_UTIL.getApp();

        int port = preferredPort;

        while (true) {
            try {
                app.start(port);
                System.out.println("Server Running on Port: " + port);
                break;
            } catch (Exception e) {
                if (allowFallback && e.getMessage() != null && e.getMessage().contains("Address already in use")) {
                    port++;
                    System.out.println("Port: " + (port - 1) + " is Busy | Retrying on Port: " + port);
                } else {
                    throw e;
                }
            }
        }

        return app;
    }
}
