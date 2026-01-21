package com.revature.Controller;

import com.revature.Model.Recipe;
import com.revature.Service.AuthenticationService;
import com.revature.Service.RecipeService;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class RecipeControllerTest {
    @Test
    public void testGetRecipesWithRecipeName() throws Exception {
        RecipeService recipeService = mock(RecipeService.class);
        AuthenticationService authService = mock(AuthenticationService.class);
        List<Recipe> mockRecipes = Collections.singletonList(new Recipe("Grilled Cheese", "Grill bread and cheese"));
        when(recipeService.searchRecipes("Cheese")).thenReturn(mockRecipes);

        Context context = mock(Context.class);
        when(context.queryParam("name")).thenReturn("Cheese");

        Handler handler = new RecipeController(recipeService, authService).getAllRecipes;
        handler.handle(context);

        verify(context).status(200);
        verify(context).json(mockRecipes);
    }

    @Test
    public void testRecipesWithNoParams() throws Exception {
        RecipeService recipeService = mock(RecipeService.class);
        AuthenticationService authService = mock(AuthenticationService.class);
        List<Recipe> allRecipes = Arrays.asList(new Recipe("Apple Pie"), new Recipe("Grilled Cheese"),
                new Recipe("Steak"));
        when(recipeService.searchRecipes(null)).thenReturn(allRecipes);

        Context context = mock(Context.class);
        when(context.queryParam("name")).thenReturn(null);
        when(context.queryParam("ingredient")).thenReturn(null);

        Handler getRecipesHandler = new RecipeController(recipeService, authService).getAllRecipes;
        getRecipesHandler.handle(context);

        verify(context).status(200);
        verify(context).json(allRecipes);
    }

    @Test
    public void testGetRecipesWithNoResults() throws Exception {
        RecipeService recipeService = mock(RecipeService.class);
        AuthenticationService authService = mock(AuthenticationService.class);
        when(recipeService.searchRecipes("Nonexistent Recipe")).thenReturn(Collections.emptyList());

        Context context = mock(Context.class);
        when(context.queryParam("name")).thenReturn("Nonexistent Recipe");

        Handler handler = new RecipeController(recipeService, authService).getAllRecipes;
        handler.handle(context);

        verify(context).status(404);
        verify(context).result("No Recipes Found");
    }
}
