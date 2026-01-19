package com.revature.Model;

public class RecipeIngredient {
    private int id;
    private String name;
    private double volume;
    private String unit;

    public RecipeIngredient() {
        super();
    }

    public RecipeIngredient(Ingredient ingredient, double volume, String unit) {
        super();
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.volume = volume;
        this.unit = unit;
    }

    public RecipeIngredient(int id, String name, double volume, String unit) {
        super();
        this.id = id;
        this.name = name;
        this.volume = volume;
        this.unit = unit;
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}