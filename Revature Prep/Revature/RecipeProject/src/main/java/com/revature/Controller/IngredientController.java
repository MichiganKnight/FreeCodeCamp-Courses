package com.revature.Controller;

import com.revature.Model.Ingredient;
import com.revature.Service.IngredientService;
import com.revature.Util.Page;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.Optional;

public class IngredientController {
    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public void getIngredient(Context context) {
        int id = Integer.parseInt(context.pathParam("id"));

        Optional<Ingredient> ingredient = ingredientService.getIngredientById(id);
        if (ingredient.isPresent()) {
            context.json(ingredient.get());
            context.status(200);
        } else {
            context.status(404);
        }
    }

    public void deleteIngredient(Context context) {
        int id = Integer.parseInt(context.pathParam("id"));
        ingredientService.deleteIngredient(id);
        context.status(204);
    }

    public void updateIngredient(Context context) {
        int id = Integer.parseInt(context.pathParam("id"));

        Optional<Ingredient> ingredient = ingredientService.getIngredientById(id);
        if (ingredient.isPresent()) {
            Ingredient updatedIngredient = context.bodyAsClass(Ingredient.class);
            ingredientService.saveIngredient(updatedIngredient);
            context.status(204);
        } else {
            context.status(404);
        }
    }

    public void createIngredient(Context context) {
        Ingredient ingredient = context.bodyAsClass(Ingredient.class);
        ingredientService.saveIngredient(ingredient);
        context.status(201);
    }

    public void getIngredients(Context context) {
        String term = getParamAsClassOrElse(context, "term", String.class, null);
        if (context.queryParam("page") != null) {
            int page = getParamAsClassOrElse(context, "page", Integer.class, 1);
            int pageSize = getParamAsClassOrElse(context, "pageSize", Integer.class, 10);
            String sortBy = getParamAsClassOrElse(context, "sortBy", String.class, "id");
            String sortDirection = getParamAsClassOrElse(context, "sortDirection", String.class, "asc");
            Page<Ingredient> ingredients = ingredientService.searchIngredients(term, page, pageSize, sortBy, sortDirection);

            context.json(ingredients);

            return;
        }

        context.json(ingredientService.searchIngredients(term));
    }

    /**
     * A helper method to get a query parameter as a class, or return a default value if it's not present.
     *
     * @param <T> Type of the query parameter
     * @param context Javalin Context object
     * @param queryTerm Query parameter name
     * @param tClass Class of the query parameter
     * @param defaultValue Default value to return if the query parameter is not present
     * @return Query parameter value as the specified class, or the default value if it's not present
     */
    private <T> T getParamAsClassOrElse(Context context, String queryTerm, Class<T> tClass, T defaultValue) {
        if (context.queryParam(queryTerm) != null) {
            return context.queryParamAsClass(queryTerm, tClass).get();
        } else {
            return defaultValue;
        }
    }

    public void configureRoutes(Javalin app) {
        app.get("/ingredients", this::getIngredients);
        app.get("/ingredients/{id}", this::getIngredient);
        app.post("/ingredients", this::createIngredient);
        app.put("/ingredients/{id}", this::updateIngredient);
        app.delete("/ingredients/{id}", this::deleteIngredient);
    }
}
