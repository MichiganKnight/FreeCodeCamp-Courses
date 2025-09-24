package dev.michiganknight.recipemanager.model;

public class DessertRecipe extends Recipe {
    public DessertRecipe(int id, String name) {
        super(id, name);
    }

    @Override
    public String getType() {
        return "Dessert";
    }
}
