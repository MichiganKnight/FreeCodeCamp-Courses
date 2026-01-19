package com.revature.Model;

import java.util.List;

public class Recipe {
    public int id;
    private String name;
    private String instructions;
    private Chef chef;
    private List<RecipeIngredient> ingredients;

    public Recipe() {}

    public Recipe(String name) {
        this.name = name;
    }

    public Recipe(String name, String instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    public Recipe(int id, String name, String instructions, Chef chef) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.chef = chef;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Chef getChef() {
        return chef;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Recipe recipe)) return false;
        return id == recipe.id && name.equals(recipe.name);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", chef=" + (chef != null ? chef.getUsername() : "Unknown") +
                '}';
    }
}
