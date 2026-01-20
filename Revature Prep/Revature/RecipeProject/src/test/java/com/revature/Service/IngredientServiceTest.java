package com.revature.Service;

import com.revature.DAO.IngredientDAO;
import com.revature.Model.Ingredient;
import com.revature.Util.Page;
import com.revature.Util.PageOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class IngredientServiceTest {
    private IngredientService ingredientService;
    private IngredientDAO ingredientDAO;
    List<Ingredient> MOCKS;

    @BeforeEach
    void setUpMocks() {
        ingredientDAO = mock(IngredientDAO.class);
        ingredientService = new IngredientService(ingredientDAO);
        MOCKS = Arrays.asList(
                new Ingredient(1, "carrot"),
                new Ingredient(2, "potato"),
                new Ingredient(3, "tomato"),
                new Ingredient(4, "lemon"),
                new Ingredient(5, "rice"),
                new Ingredient(6, "stone"));
    }

    @Test
    public void fetchOneIngredient() {
        when(ingredientDAO.getIngredientById(1)).thenReturn(MOCKS.get(0));
        Optional<Ingredient> ingredient = ingredientService.getIngredientById(1);
        Assertions.assertTrue(ingredient.isPresent(), () -> "Ingredient Should be Present");
        Assertions.assertEquals(MOCKS.get(0), ingredient.get(), () -> "Ingredient Should Match");
    }

    @Test
    public void failToFetchOneIngredient() {
        when(ingredientDAO.getIngredientById(1)).thenReturn(null);
        Optional<Ingredient> ingredient = ingredientService.getIngredientById(1);
        Assertions.assertTrue(ingredient.isEmpty(), () -> "Ingredient Should not be Present");
    }

    @Test
    public void saveNewIngredient() {
        Ingredient newIngredient = new Ingredient("new ingredient");
        ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);
        when(ingredientDAO.createIngredient(any(Ingredient.class))).thenReturn(42);
        ingredientService.saveIngredient(newIngredient);
        verify(ingredientDAO).createIngredient(ingredientCaptor.capture());
        Ingredient captureIngredient = ingredientCaptor.getValue();
        Assertions.assertEquals(42, captureIngredient.getId(), () -> "Services Should Set the 'id' of a Newly Created Ingredient");
    }

    @Test
    public void updateIngredient() {
        Ingredient existingIngredient = new Ingredient(42, "new ingredient");
        ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);
        doNothing().when(ingredientDAO).updateIngredient(any(Ingredient.class));
        ingredientService.saveIngredient(existingIngredient);
        verify(ingredientDAO).updateIngredient(ingredientCaptor.capture());
        Ingredient captureIngredient = ingredientCaptor.getValue();
        Assertions.assertEquals(existingIngredient, captureIngredient, () -> "Services Should Not Change the 'id' of an Existing Ingredient");
    }

    @Test
    public void deleteIngredient() throws SQLException {
        when(ingredientDAO.getIngredientById(1)).thenReturn(MOCKS.get(0));
        doNothing().when(ingredientDAO).deleteIngredient(any(Ingredient.class));
        ArgumentCaptor<Ingredient> ingredientCaptor = ArgumentCaptor.forClass(Ingredient.class);
        ingredientService.deleteIngredient(1);
        verify(ingredientDAO).deleteIngredient(ingredientCaptor.capture());
        verify(ingredientDAO).getIngredientById(1);
    }

    @Test
    public void searchForListOfAllIngredients() {
        when(ingredientDAO.getAllIngredients()).thenReturn(MOCKS);
        List<Ingredient> ingredients = ingredientService.searchIngredients(null);
        Assertions.assertIterableEquals(MOCKS, ingredients, () -> "Services Should Return the List of All Ingredients");
    }

    @Test
    public void searchForFilteredListOfIngredients() {
        when(ingredientDAO.searchIngredients("to")).thenReturn(Arrays.asList(MOCKS.get(1), MOCKS.get(2), MOCKS.get(5)));
        List<Ingredient> ingredients = ingredientService.searchIngredients("to");
        Assertions.assertIterableEquals(Arrays.asList(MOCKS.get(1), MOCKS.get(2), MOCKS.get(5)), ingredients, () -> "Services Should Return the Filtered List of Ingredients");
    }

    @Test
    public void searchReturnsEmptyList() {
        when(ingredientDAO.searchIngredients("Bal")).thenReturn(Arrays.asList());
        List<Ingredient> ingredients = ingredientService.searchIngredients("Bal");
        Assertions.assertTrue(ingredients.isEmpty(), () -> "Services Should Return an Empty List if No Ingredients Match the Search Term");
    }

    @Test
    public void searchForPageOfAllIngredients() {
        when(ingredientDAO.getAllIngredients(any(PageOptions.class)))
                .thenReturn(new Page<Ingredient>(1, 5, 1, 5, MOCKS));
        Page<Ingredient> ingredients = ingredientService.searchIngredients(null, 1, 5, "id", "asc");
        ArgumentCaptor<PageOptions> optionsCaptor = ArgumentCaptor.forClass(PageOptions.class);
        verify(ingredientDAO).getAllIngredients(optionsCaptor.capture());
        Assertions.assertEquals(new Page<Ingredient>(1, 5, 1, 5, MOCKS), ingredients, () -> "Services Should Return the Page of Ingredients");
    }

    @Test
    public void searchForFilteredPageOfRecipes() {
        when(ingredientDAO.searchIngredients(anyString(), any(PageOptions.class)))
                .thenReturn(new Page<Ingredient>(1, 3, 1, 3, Arrays.asList(MOCKS.get(1), MOCKS.get(2), MOCKS.get(5))));
        Page<Ingredient> ingredients = ingredientService.searchIngredients("to", 1, 5, "id", "asc");
        ArgumentCaptor<PageOptions> optionsCaptor = ArgumentCaptor.forClass(PageOptions.class);
        ArgumentCaptor<String> termCaptor = ArgumentCaptor.forClass(String.class);
        verify(ingredientDAO).searchIngredients(termCaptor.capture(), optionsCaptor.capture());
        Assertions.assertEquals(new Page<Ingredient>(1, 3, 1, 3, Arrays.asList(MOCKS.get(1), MOCKS.get(2), MOCKS.get(5))), ingredients, () -> "Services Should Return the Filtered Page of Ingredients");
    }

    @Test
    public void searchReturnsEmptyPage() {
        when(ingredientDAO.searchIngredients(anyString(), any(PageOptions.class)))
                .thenReturn(new Page<Ingredient>(0, 0, 0, 0, Collections.emptyList()));
        Page<Ingredient> ingredients = ingredientService.searchIngredients("Bal", 1, 5, "id", "asc");
        ArgumentCaptor<PageOptions> optionsCaptor = ArgumentCaptor.forClass(PageOptions.class);
        ArgumentCaptor<String> termCaptor = ArgumentCaptor.forClass(String.class);
        verify(ingredientDAO).searchIngredients(termCaptor.capture(), optionsCaptor.capture());
        Assertions.assertEquals(new Page<Ingredient>(0, 0, 0, 0, Collections.emptyList()), ingredients, () -> "Services Should Return an Empty Page if No Ingredients Match the Search Term");
    }
}
