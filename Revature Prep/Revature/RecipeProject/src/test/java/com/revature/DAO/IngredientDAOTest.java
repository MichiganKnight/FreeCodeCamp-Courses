package com.revature.DAO;

import com.revature.Model.Ingredient;
import com.revature.Util.ConnectionUtil;
import com.revature.Util.DBUtil;
import com.revature.Util.Page;
import com.revature.Util.PageOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.revature.Utils.TestingUtils.assertCountDifference;

public class IngredientDAOTest {
    private List<Ingredient> ingredientList = new ArrayList<>();
    private IngredientDAO ingredientDAO;
    private String countSelStatement = "SELECT COUNT(*) FROM INGREDIENT";

    @BeforeEach
    void setupTestsData() throws SQLException {
        DBUtil.RUN_SQL();
        ingredientList.clear();
        ingredientDAO = new IngredientDAO(new ConnectionUtil());
        ingredientList.addAll(Arrays.asList(
                new Ingredient(1, "carrot"),
                new Ingredient(2, "potato"),
                new Ingredient(3, "tomato"),
                new Ingredient(4, "lemon"),
                new Ingredient(5, "rice"),
                new Ingredient(6, "stone")));
    }

    @Test
    public void createIngredientTest() {
        Ingredient ingredient = new Ingredient("testIngredient");
        assertCountDifference(1, "Expected Ingredient Count to be 1 or More", countSelStatement, () -> {
            int newId = ingredientDAO.createIngredient(ingredient);

            Assertions.assertEquals(7, newId, "The ID of the Newly Created Ingredient Should be 7");
        });
    }

    @Test
    public void readOneTest() {
        Ingredient ingredient = ingredientDAO.getIngredientById(1);
        Assertions.assertEquals(ingredientList.get(0), ingredient, "The Returned Ingredient Doesn't Match the Expected Ingredient. Expected: " + ingredientList.get(0) + ", Actual: " + ingredient);
    }

    @Test
    public void deleteIngredientTest() throws SQLException {
        Ingredient ingredient = ingredientDAO.getIngredientById(1);
        assertCountDifference(-1, "Expected Ingredient Count to be 1 Less", countSelStatement, () -> ingredientDAO.deleteIngredient(ingredient));
        Assertions.assertEquals(null, ingredientDAO.getIngredientById(1), "The Ingredient With ID 1 Should be Deleted.");
    }

    @Test
    public void updateIngredientTest() {
        Ingredient ingredient = ingredientDAO.getIngredientById(1);
        ingredient.setName("newName");
        ingredientDAO.updateIngredient(ingredient);
        Ingredient updatedIngredient = ingredientDAO.getIngredientById(1);
        Assertions.assertEquals(ingredient.getName(), updatedIngredient.getName(), "The Updated Ingredient Name Doesn't Match the Expected Ingredient Name. Expected: " + ingredient.getName() + ", Actual: " + updatedIngredient.getName());
    }

    @Test
    public void getAllIngredientsTest() {
        List<Ingredient> ingredients = ingredientDAO.getAllIngredients();
        Assertions.assertEquals(ingredientList.size(), ingredients.size(), "The Number of Ingredients Returned Doesn't Match the Expected Number of Ingredients. Expected: " + ingredientList.size() + ", Actual: " + ingredients.size());
        Assertions.assertIterableEquals(ingredientList, ingredients, "The Returned Ingredients Don't Match the Expected Ingredients. Expected: " + ingredientList + ", Actual: " + ingredients);
    }

    @Test
    public void getAndPageAllIngredientsTest() {
        PageOptions pageOptions = new PageOptions(1, 2, "ID", "ASC");
        Page<Ingredient> expectedIngredients = new Page<>(1, 2, ingredientList.size() / 2, ingredientList.size(), ingredientList.subList(0, 2));
        Page<Ingredient> ingredients = ingredientDAO.getAllIngredients(pageOptions);

        Assertions.assertIterableEquals(expectedIngredients.getItems(), ingredients.getItems(), "The Returned Ingredients Don't Match the Expected Ingredients. Expected: " + expectedIngredients.getItems() + ", Actual: " + ingredients.getItems());
    }

    @Test
    public void searchIngredientsTest() {
        List<Ingredient> ingredients = ingredientDAO.searchIngredients("to");
        List<Ingredient> expectedIngredients = Arrays.asList(ingredientList.get(1), ingredientList.get(2), ingredientList.get(5));

        Assertions.assertIterableEquals(expectedIngredients, ingredients, "The Returned Ingredients Don't Match the Expected Ingredients. Expected: " + expectedIngredients + ", Actual: " + ingredients);
    }

    @Test
    public void searchAndPageIngredientsTest() {
        PageOptions pageOptions = new PageOptions(1, 2, "ID", "ASC");
        Page<Ingredient> expectedIngredients = new Page<>(1, 2, 2, 3, Arrays.asList(ingredientList.get(1), ingredientList.get(2)));
        Page<Ingredient> ingredients = ingredientDAO.searchIngredients("to", pageOptions);

        Assertions.assertIterableEquals(expectedIngredients.getItems(), ingredients.getItems(), "The Returned Ingredients Don't Match the Expected Ingredients. Expected: " + expectedIngredients.getItems() + ", Actual: " + ingredients.getItems());
    }
}
