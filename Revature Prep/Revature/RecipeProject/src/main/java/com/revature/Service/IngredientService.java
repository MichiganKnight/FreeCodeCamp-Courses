package com.revature.Service;

import com.revature.DAO.IngredientDAO;
import com.revature.Model.Ingredient;
import com.revature.Util.Page;
import com.revature.Util.PageOptions;

import java.util.List;
import java.util.Optional;

public class IngredientService {
    private IngredientDAO ingredientDAO;

    public IngredientService(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }

    public Optional<Ingredient> getIngredientById(int id) {
        return Optional.ofNullable(ingredientDAO.getIngredientById(id));
    }

    public void saveIngredient(Ingredient ingredient) {
        if (ingredient.getId() == 0) {
            int id = ingredientDAO.createIngredient(ingredient);
            ingredient.setId(id);
        } else {
            ingredientDAO.updateIngredient(ingredient);
        }
    }

    public void deleteIngredient(int id) {
        Ingredient ingredient = ingredientDAO.getIngredientById(id);
        if (ingredient != null) {
            ingredientDAO.deleteIngredient(ingredient);
        }
    }

    public List<Ingredient> searchIngredients(String term) {
        if (term == null) {
            return ingredientDAO.getAllIngredients();
        } else {
            return ingredientDAO.searchIngredients(term);
        }
    }

    public Page<Ingredient> searchIngredients(String term, int page, int pageSize, String sortBy, String sortDirection) {
        PageOptions pageOptions = new PageOptions(page, pageSize, sortBy, sortDirection);
        if (term == null) {
            return ingredientDAO.getAllIngredients(pageOptions);
        } else {
            return ingredientDAO.searchIngredients(term, pageOptions);
        }
    }
}
