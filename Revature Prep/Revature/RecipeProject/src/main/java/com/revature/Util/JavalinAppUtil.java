package com.revature.Util;

import com.revature.Controller.AuthenticationController;
import com.revature.Controller.IngredientController;
import com.revature.Controller.RecipeController;
import io.javalin.Javalin;

public class JavalinAppUtil {
    private RecipeController recipeController;
    private AuthenticationController authController;
    private IngredientController ingredientController;

    public JavalinAppUtil(RecipeController recipeController, AuthenticationController authController, IngredientController ingredientController) {
        this.recipeController = recipeController;
        this.authController = authController;
        this.ingredientController = ingredientController;
    }

    public Javalin getApp() {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.anyHost();
                });
            });
        });

        recipeController.configureRoutes(app);
        authController.configureRoutes(app);
        ingredientController.configureRoutes(app);

        app.before("/recipes/*", new AdminMiddleware("DELETE"));
        app.before("/ingredients/*", new AdminMiddleware("UPDATE", "CREATE", "DELETE"));

        return app;
    }
}
