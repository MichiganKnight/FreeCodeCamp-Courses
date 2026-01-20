package com.revature.Service;

import com.revature.DAO.RecipeDAO;
import com.revature.Model.Recipe;
import com.revature.Util.Page;
import com.revature.Util.PageOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class RecipeServiceTest {
    private RecipeService recipeService;
    private RecipeDAO recipeDAO;
    private List<Recipe> MOCKS;

    @BeforeEach
    void setUpMocks() {
        recipeDAO = mock(RecipeDAO.class);
        recipeService = new RecipeService(recipeDAO);
        MOCKS = Arrays.asList(
                new Recipe(1, "Pasta", "Boil water, add pasta, add sauce", null),
                new Recipe(2, "Pizza", "Make dough, add sauce, add cheese, bake", null),
                new Recipe(3, "Salad", "Chop lettuce, add dressing", null),
                new Recipe(4, "Sandwich", "Put stuff between bread", null),
                new Recipe(5, "Soup", "Boil water, add stuff", null));
    }

    @Test
    public void fetchOneRecipe() {
        when(recipeDAO.getRecipeById(1)).thenReturn(MOCKS.get(0));
        Optional<Recipe> recipe = recipeService.getRecipeById(1);
        Assertions.assertTrue(recipe.isPresent(), () -> "Recipe Should be Present");
        Assertions.assertEquals(MOCKS.get(0), recipe.get(), () -> "Recipe Should Match");
    }

    @Test
    public void failToFetchOneRecipe() {
        when(recipeDAO.getRecipeById(1)).thenReturn(null);
        Optional<Recipe> recipe = recipeService.getRecipeById(1);
        Assertions.assertTrue(recipe.isEmpty(), () -> "Recipe Should not be Present");
    }

    @Test
    public void saveNewRecipe() {
        Recipe newRecipe = new Recipe("New Recipe", "New Recipe Instructions");
        ArgumentCaptor<Recipe> recipeCaptor = ArgumentCaptor.forClass(Recipe.class);
        when(recipeDAO.createRecipe(any(Recipe.class))).thenReturn(42);
        recipeService.saveRecipe(newRecipe);
        verify(recipeDAO).createRecipe(recipeCaptor.capture());
        Recipe captureRecipe = recipeCaptor.getValue();
        Assertions.assertEquals(42, captureRecipe.getId(), () -> "Services Should Set the 'id' of a Newly Created Recipe");
    }

    @Test
    public void updateRecipe() {
        Recipe existingRecipe = new Recipe(42, "Existing Recipe", "Existing Recipe Instructions", null);
        ArgumentCaptor<Recipe> recipeCaptor = ArgumentCaptor.forClass(Recipe.class);
        doNothing().when(recipeDAO).updateRecipe(any(Recipe.class));
        when(recipeDAO.getRecipeById(anyInt())).thenReturn(existingRecipe);
        recipeService.saveRecipe(existingRecipe);
        verify(recipeDAO).updateRecipe(recipeCaptor.capture());
        Recipe captureRecipe = recipeCaptor.getValue();
        Assertions.assertEquals(existingRecipe, captureRecipe, () -> "Services Should Not Change the 'id' of an Existing Recipe");
    }

    @Test
    public void deleteRecipe() {
        when(recipeDAO.getRecipeById(1)).thenReturn(MOCKS.get(0));
        doNothing().when(recipeDAO).deleteRecipe(any(Recipe.class));
        ArgumentCaptor<Recipe> recipeCaptor = ArgumentCaptor.forClass(Recipe.class);
        recipeService.deleteRecipe(1);
        verify(recipeDAO).deleteRecipe(recipeCaptor.capture());
        verify(recipeDAO).getRecipeById(1);
    }

    @Test
    public void searchForListOfAllRecipes() {
        when(recipeDAO.getAllRecipes()).thenReturn(MOCKS);
        List<Recipe> recipes = recipeService.searchRecipes(null);
        Assertions.assertIterableEquals(MOCKS, recipes, () -> "Services Should Return the List of All Recipes");
    }

    @Test
    public void searchForFilteredListOfRecipes() {
        when(recipeDAO.searchRecipesByTerm("to")).thenReturn(Arrays.asList(MOCKS.get(1), MOCKS.get(2)));
        List<Recipe> recipes = recipeService.searchRecipes("to");
        Assertions.assertIterableEquals(Arrays.asList(MOCKS.get(1), MOCKS.get(2)), recipes, () -> "Services Should Return the Filtered List of Recipes");
    }

    @Test
    public void searchForPageOfAllRecipes() {
        when(recipeDAO.getAllRecipes(any(PageOptions.class))).thenReturn(new Page<Recipe>(1, 5, 1, 5, MOCKS));
        Page<Recipe> recipes = recipeService.searchRecipes(null, 1, 5, "id", "asc");
        ArgumentCaptor<PageOptions> optionsCaptor = ArgumentCaptor.forClass(PageOptions.class);
        verify(recipeDAO).getAllRecipes(optionsCaptor.capture());
        Assertions.assertEquals(new Page<Recipe>(1, 5, 1, 5, MOCKS), recipes, () -> "Services Should Return the Page of Recipes");
    }

    @Test
    public void searchForFilteredPageOfRecipes() {
        when(recipeDAO.searchRecipesByTerm(anyString(), any(PageOptions.class))).thenReturn(
                new Page<Recipe>(1, 5, 1, 5, Arrays.asList(MOCKS.get(0), MOCKS.get(2), MOCKS.get(3), MOCKS.get(4))));
        Page<Recipe> recipes = recipeService.searchRecipes("a", 1, 5, "id", "asc");
        ArgumentCaptor<PageOptions> optionsCaptor = ArgumentCaptor.forClass(PageOptions.class);
        ArgumentCaptor<String> termCaptor = ArgumentCaptor.forClass(String.class);
        verify(recipeDAO).searchRecipesByTerm(termCaptor.capture(), optionsCaptor.capture());
        Assertions.assertEquals(new Page<Recipe>(1, 5, 1, 5, Arrays.asList(MOCKS.get(0), MOCKS.get(2), MOCKS.get(3), MOCKS.get(4))), recipes, () -> "Services Should Return the Filtered Page of Recipes");
    }

    @Test
    public void searchReturnsEmptyPage() {
        when(recipeDAO.searchRecipesByTerm(anyString(), any(PageOptions.class)))
                .thenReturn(new Page<Recipe>(1, 5, 0, 0, Collections.emptyList()));
        Page<Recipe> recipes = recipeService.searchRecipes("Bal", 1, 5, "id", "asc");
        ArgumentCaptor<PageOptions> optionsCaptor = ArgumentCaptor.forClass(PageOptions.class);
        ArgumentCaptor<String> termCaptor = ArgumentCaptor.forClass(String.class);
        verify(recipeDAO).searchRecipesByTerm(termCaptor.capture(), optionsCaptor.capture());
        Assertions.assertEquals(new Page<Recipe>(1, 5, 0, 0, Collections.emptyList()), recipes, () -> "Services Should Return an Empty Page if No Recipes Match the Search Term");
    }
}
