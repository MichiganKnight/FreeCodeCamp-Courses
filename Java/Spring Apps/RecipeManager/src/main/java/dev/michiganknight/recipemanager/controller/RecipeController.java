package dev.michiganknight.recipemanager.controller;

import dev.michiganknight.recipemanager.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeController {
    private List<Recipe> recipes = new ArrayList<>();

    private DessertRecipe dessertRecipe = new DessertRecipe(1, "Chocolate Cake");

    @GetMapping
    public List<Recipe> getRecipes() {
        return recipes;
    }

    @PostMapping
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        recipes.add(recipe);
        return recipe;
    }
}
