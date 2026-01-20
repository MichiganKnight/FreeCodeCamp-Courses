package com.revature.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecipeTest {
    @Test
    public void testCreationOfRecipe() {
        Recipe recipe = new Recipe();
        Assertions.assertNotNull(recipe, "Recipe Should Not Be Null");
    }

    @Test
    public void testCreationOfRecipeWithName() {
        Recipe recipe = new Recipe("carrot soup");
        Assertions.assertNotNull(recipe, "Recipe Should Not Be Null");
    }

    @Test
    public void testSetRecipeName() {
        Recipe recipe = new Recipe();
        recipe.setName("carrot soup");
    }

    @Test
    public void testGetRecipeName() {
        Recipe recipe = new Recipe("carrot soup");
        Assertions.assertEquals("carrot soup", recipe.getName(), "Recipe Name Should Match");
    }

    @Test
    public void testSetRecipeInstructions() {
        Recipe recipe = new Recipe();
        recipe.setInstructions("Put carrot in water. Boil. Maybe salt.");
    }

    @Test
    public void testGetRecipeInstructions() {
        Recipe recipe = new Recipe("carrot soup", "Put carrot in water. Boil. Maybe salt.");
        Assertions.assertEquals("Put carrot in water. Boil. Maybe salt.", recipe.getInstructions(), "Recipe Instructions Should Match");
    }

    @Test
    public void testSetRecipeChef() {
        Recipe recipe = new Recipe();
        Chef chef = new Chef();
        recipe.setChef(chef);
    }

    @Test
    public void testGetRecipeChef() {
        Chef chef = new Chef();
        Recipe recipe = new Recipe(1, "carrot soup", "Put carrot in water. Boil. Maybe salt.", chef);
        Assertions.assertEquals(chef, recipe.getChef(), "Recipe Chef Should Match");
    }
}
