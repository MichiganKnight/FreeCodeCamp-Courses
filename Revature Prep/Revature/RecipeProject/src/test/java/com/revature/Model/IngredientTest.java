package com.revature.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IngredientTest {
    Class<?> ingredientClass = Ingredient.class;

    @Test
    public void noArgsConstructor() {
        try {
            ingredientClass.getConstructor();
        } catch (NoSuchMethodException e) {
            Assertions.fail("The Ingredient Class Does Not Have a No-Argument Constructor", e);
        }
    }

    @Test
    public void parameterizedConstructor() {
        try {
            ingredientClass.getConstructor(String.class);
            ingredientClass.getConstructor(int.class, String.class);
        } catch (NoSuchMethodException e) {
            Assertions.fail("The Ingredient Class Does Not Contain the Correct Parameterized Constructors", e);
        }
    }

    @Test
    public void instanceVariables() {
        try {
            ingredientClass.getDeclaredField("id");
            ingredientClass.getDeclaredField("name");
        } catch (NoSuchFieldException e) {
            Assertions.fail("The Ingredient Class Does Not Contain 'id' and 'name' Instance Variables", e);
        }
    }

    @Test
    public void getterMethods() {
        try {
            ingredientClass.getMethod("getId");
            ingredientClass.getMethod("getName");
        } catch (NoSuchMethodException e) {
            Assertions.fail("The Ingredient Class Does Not Contain Getter Methods for 'id' and 'name'", e);
        }
    }

    @Test
    public void setterMethods() {
        try {
            ingredientClass.getMethod("setId", int.class);
            ingredientClass.getMethod("setName", String.class);
        } catch (NoSuchMethodException e) {
            Assertions.fail("The Ingredient Class Does Not Contain Setter Methods for 'id' and 'name'", e);
        }
    }

    @Test
    public void objectContract() {
        try {
            ingredientClass.getDeclaredMethod("equals", Object.class);
            ingredientClass.getDeclaredMethod("hashCode");
        } catch (NoSuchMethodException e) {
            Assertions.fail("The Ingredient Class Should Have Overridden the 'equals' and 'hashCode' methods", e);
        }
    }

    @Test
    public void settersAndGettersImpl() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setName("test");

        Assertions.assertEquals(1, ingredient.getId(), "Ingredient Class Should Implement the 'setId' and 'getId' Methods");
        Assertions.assertEquals("test", ingredient.getName(), "Ingredient Class Should Implement the 'setName' and 'getName' Methods");
    }
}
