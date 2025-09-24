package dev.michiganknight.recipemanager.model;

public abstract class Recipe {
    private int id;
    private String name;

    public Recipe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String getType();
}