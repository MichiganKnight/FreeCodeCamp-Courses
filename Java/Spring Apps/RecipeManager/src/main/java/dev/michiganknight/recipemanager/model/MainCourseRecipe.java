package dev.michiganknight.recipemanager.model;

public class MainCourseRecipe extends Recipe {
    public MainCourseRecipe(int id, String name) {
        super(id, name);
    }

    @Override
    public String getType() {
        return "Main Course";
    }
}
