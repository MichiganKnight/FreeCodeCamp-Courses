package com.revature.Service;

import com.revature.DAO.RecipeDAO;
import com.revature.Model.Recipe;
import com.revature.Util.Page;
import com.revature.Util.PageOptions;

import java.util.List;
import java.util.Optional;

public class RecipeService {
    private RecipeDAO recipeDAO;

    public RecipeService(RecipeDAO recipeDAO) {
        this.recipeDAO = recipeDAO;
    }

    public Optional<Recipe> getRecipeById(int id) {
        return Optional.ofNullable(recipeDAO.getRecipeById(id));
    }

    public void saveRecipe(Recipe recipe) {
        if (recipe.getId() == 0) {
            int id = recipeDAO.createRecipe(recipe);
            recipe.setId(id);
        } else {
            recipeDAO.updateRecipe(recipe);
        }
    }

    public Page<Recipe> searchRecipes(String term, int page, int pageSize, String sortBy, String sortDirection) {
        PageOptions pageOptions = new PageOptions(page, pageSize, sortBy, sortDirection);
        if (term == null) {
            return recipeDAO.getAllRecipes(pageOptions);
        } else {
            return recipeDAO.searchRecipesByTerm(term, pageOptions);
        }
    }

    public List<Recipe> searchRecipes(String term) {
        if (term == null) {
            return recipeDAO.getAllRecipes();
        } else {
            return recipeDAO.searchRecipesByTerm(term);
        }
    }

    public boolean deleteRecipe(int id) {
        Recipe recipe = recipeDAO.getRecipeById(id);
        if (recipe != null) {
            recipeDAO.deleteRecipe(recipe);
            return true;
        } else {
            return false;
        }
    }
}
