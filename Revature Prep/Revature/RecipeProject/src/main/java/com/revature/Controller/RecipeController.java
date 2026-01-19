package com.revature.Controller;

import com.revature.Model.Chef;
import com.revature.Model.Recipe;
import com.revature.Service.AuthenticationService;
import com.revature.Service.RecipeService;
import com.revature.Util.Page;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecipeController {
    private RecipeService recipeService;
    private AuthenticationService authService;

    public RecipeController(RecipeService recipeService, AuthenticationService authService) {
        this.recipeService = recipeService;
        this.authService = authService;
    }

    public Handler getAllRecipes = context -> {
        String term = getParamAsClassOrElse(context, "term", String.class, null);

        if (context.queryParam("page") != null) {
            int page = getParamAsClassOrElse(context, "page", Integer.class, 1);
            int pageSize = getParamAsClassOrElse(context, "pageSize", Integer.class, 10);
            String sortBy = getParamAsClassOrElse(context, "sortBy", String.class, "id");
            String sortDirection = getParamAsClassOrElse(context, "sortDirection", String.class, "asc");
            Page<Recipe> recipePage = recipeService.searchRecipes(term, page, pageSize, sortBy, sortDirection);

            context.json(recipePage);
        } else {
            String ingredient = context.queryParam("ingredient");
            String recipeName = context.queryParam("name");

            List<Recipe> recipes = new ArrayList<>();
            if (ingredient == null && recipeName == null) {
                recipes = recipeService.searchRecipes(null);
            } else if (ingredient == null && recipeName != null) {
                recipes = recipeService.searchRecipes(recipeName);
            }

            if (recipes.isEmpty()) {
                context.status(404).result("No Recipes Found");
            } else {
                context.status(200);
                context.json(recipes);
            }
        }
    };

    public Handler getRecipeById = context -> {
        int id = Integer.parseInt(context.queryParam("id"));
        Optional<Recipe> recipe = recipeService.getRecipeById(id);

        if (recipe.isPresent()) {
            context.json(recipe.get());
            context.status(200);
        } else {
            context.status(404).result("Recipe Not Found");
        }
    };

    public Handler createRecipe = context -> {
        Chef chef = authService.getChefFromSessionToken(context.header("Authorization").split(" ")[1]);
        if (chef == null) {
            context.status(401);
        } else {
            Recipe recipe = context.bodyAsClass(Recipe.class);
            recipe.setId(0);
            recipe.setChef(chef);
            recipeService.saveRecipe(recipe);

            context.status(201);
        }
    };

    public Handler deleteRecipe = context -> {
        try {
            int id = Integer.parseInt(context.pathParam("id"));
            boolean deleted = recipeService.deleteRecipe(id);

            if (deleted) {
                context.status(200).result("Recipe Deleted Successfully");
            } else {
                context.status(404).result("Recipe Not Found");
            }
        } catch (NumberFormatException e) {
            context.status(400).result("Invalid Recipe ID");
        } catch (Exception e) {
            context.status(500).result("An Error Occurred While Deleting the Recipe");
        }
    };

    public Handler updateRecipe = context -> {
        int id = Integer.parseInt(context.pathParam("id"));
        Recipe recipe = context.bodyAsClass(Recipe.class);
        Optional<Recipe> existingRecipe = recipeService.getRecipeById(id);

        if (!existingRecipe.isPresent()) {
            context.status(404).result("Recipe Not Found");

            return;
        }

        recipe.setId(id);
        recipeService.saveRecipe(recipe);
        context.status(200).json(recipe);
    };

    /**
     * A helper method to retrieve a query parameter from the context as a specific class type or return a default value if it's not present.
     *
     * @param <T> The type of the query parameter value
     * @param context The Javalin Context object
     * @param queryTerm The name of the query parameter
     * @param tClass The class type of the query parameter value
     * @param defaultValue The default value to return if the query parameter is not present
     * @return The query parameter value as the specified class type, or the default value if it's not present
     */
    private <T> T getParamAsClassOrElse(Context context, String queryTerm, Class<T> tClass, T defaultValue) {
        String paramValue = context.queryParam(queryTerm);
        if (paramValue != null) {
            if (tClass == Integer.class) {
                return tClass.cast(Integer.valueOf(paramValue));
            } else if (tClass == Boolean.class) {
                return tClass.cast(Boolean.valueOf(paramValue));
            } else {
                return tClass.cast(paramValue);
            }
        }

        return defaultValue;
    }
}
