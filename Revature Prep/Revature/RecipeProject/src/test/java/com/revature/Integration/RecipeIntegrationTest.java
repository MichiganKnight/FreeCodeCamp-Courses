package com.revature.Integration;

import com.revature.Controller.AuthenticationController;
import com.revature.Controller.IngredientController;
import com.revature.Controller.RecipeController;
import com.revature.DAO.ChefDAO;
import com.revature.DAO.IngredientDAO;
import com.revature.DAO.RecipeDAO;
import com.revature.Model.Chef;
import com.revature.Model.Recipe;
import com.revature.Service.AuthenticationService;
import com.revature.Service.ChefService;
import com.revature.Service.IngredientService;
import com.revature.Service.RecipeService;
import com.revature.Util.ConnectionUtil;
import com.revature.Util.DBUtil;
import com.revature.Util.JavalinAppUtil;
import com.revature.Util.Page;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import okhttp3.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeIntegrationTest {
    private static int PORT = 8081;
    private static String BASE_URL = "http://localhost:" + PORT;

    private List<Recipe> recipeList = new ArrayList<>();
    private List<Chef> chefList = new ArrayList<>();
    private JavalinAppUtil appUtil;
    private RecipeDAO recipeDAO;
    private RecipeService recipeService;
    private RecipeController recipeController;
    private ChefDAO chefDAO;
    private ChefService chefService;
    private IngredientDAO ingredientDAO;
    private IngredientService ingredientService;
    private IngredientController ingredientController;
    private AuthenticationService authService;
    private AuthenticationController authController;
    private String token;
    private Javalin app;
    private OkHttpClient client;

    @BeforeEach
    void setUpTestsData() throws SQLException, IOException {
        DBUtil.RUN_SQL();
        recipeList.clear();
        chefList.addAll(Arrays.asList(
                new Chef(1, "JoeCool", "snoopy@null.com", "redbarron", false),
                new Chef(2, "CharlieBrown", "goodgrief@peanuts.com", "thegreatpumpkin", false),
                new Chef(3, "RevaBuddy", "revature@revature.com", "codelikeaboss", false),
                new Chef(4, "ChefTrevin", "trevin@revature.com", "trevature", true)));
        recipeList.addAll(Arrays.asList(
                new Recipe(1, "carrot soup", "Put carrot in water.  Boil.  Maybe salt.", chefList.get(0)),
                new Recipe(2, "potato soup", "Put potato in water.  Boil.  Maybe salt.", chefList.get(1)),
                new Recipe(3, "tomato soup", "Put tomato in water.  Boil.  Maybe salt.", chefList.get(1)),
                new Recipe(4, "lemon rice soup", "Put lemon and rice in water.  Boil.  Maybe salt.", chefList.get(3)),
                new Recipe(5, "stone soup", "Put stone in water.  Boil.  Maybe salt.", chefList.get(3))));

        chefDAO = new ChefDAO(new ConnectionUtil());
        ingredientDAO = new IngredientDAO(new ConnectionUtil());
        recipeDAO = new RecipeDAO(chefDAO, ingredientDAO, new ConnectionUtil());
        recipeService = new RecipeService(recipeDAO);
        ingredientService = new IngredientService(ingredientDAO);
        chefService = new ChefService(chefDAO);
        authService = new AuthenticationService(chefService);
        authController = new AuthenticationController(chefService, authService);
        ingredientController = new IngredientController(ingredientService);
        recipeController = new RecipeController(recipeService, authService);
        appUtil = new JavalinAppUtil(recipeController, authController, ingredientController);
        app = appUtil.getApp();
        app.start(PORT);
        client = new OkHttpClient();

        Chef chef = new Chef();
        chef.setUsername(chefList.get(3).getUsername());
        chef.setPassword(chefList.get(3).getPassword());

        RequestBody chefBody = RequestBody.create(
                "{\"username\":\"" + chef.getUsername() + "\",\"password\":\"" + chef.getPassword() + "\"}",
                MediaType.get("application/json; charset=utf-8"));
        Request loginRequest = new Request.Builder().url(BASE_URL + "/login").post(chefBody).build();
        Response loginResponse = client.newCall(loginRequest).execute();
        token = loginResponse.body().string();
    }

    @AfterEach
    void tearDownTestsData() {
        app.stop();
    }

    @Test
    public void testGetRecipe() throws IOException {
        Request request = new Request.Builder().url(BASE_URL + "/recipes/2").addHeader("Authorization", token).get()
                .build();
        Response response = client.newCall(request).execute();

        Assertions.assertEquals(200, response.code(), "Should Return With a Success Status Code. Expected 200, Actual: " + response.code());
        Assertions.assertEquals(new JavalinJackson().toJsonString(recipeList.get(1), Recipe.class), response.body().string(), "Should Return The Correct Recipe as JSON");
    }

    @Test
    public void testGetAllRecipes() throws IOException {
        Request request = new Request.Builder().url(BASE_URL + "/recipes").addHeader("Authorization", token).get().build();
        Response response = client.newCall(request).execute();
        Assertions.assertEquals(200, response.code());
    }

    @Test
    public void testPostRecipe() throws IOException {
        Recipe newRecipe = new Recipe(6, "fried fish", "fish, oil, stove", chefList.get(3));
        RequestBody recipeBody = RequestBody.create(new JavalinJackson().toJsonString(newRecipe, Recipe.class),
                MediaType.get("application/json; charset=utf-8"));
        Request recipeRequest = new Request.Builder().url(BASE_URL + "/recipes")
                .addHeader("Authorization", "Bearer " + token)
                .post(recipeBody).build();
        Response postResponse = client.newCall(recipeRequest).execute();
        Assertions.assertEquals(201, postResponse.code(), postResponse.body().string());
        Request getRequest = new Request.Builder().url(BASE_URL + "/recipes/6")
                .addHeader("Authorization", "Bearer " + token).get()
                .build();
        Response getResponse = client.newCall(getRequest).execute();
        Assertions.assertEquals(200, getResponse.code());
        Assertions.assertEquals(new JavalinJackson().toJsonString(newRecipe, Recipe.class), getResponse.body().string(), "Newly Created Recipe Should Return as JSON");
    }

    @Test
    public void testPutRecipe() throws IOException {
        Recipe updatedRecipe = recipeList.get(0);
        updatedRecipe.setInstructions("Don't add salt");
        RequestBody recipeBody = RequestBody.create(new JavalinJackson().toJsonString(updatedRecipe, Recipe.class),
                MediaType.get("application/json; charset=utf-8"));
        Request recipeRequest = new Request.Builder().url(BASE_URL + "/recipes/1").addHeader("Authorization", token)
                .put(recipeBody).build();
        Response putResponse = client.newCall(recipeRequest).execute();
        Assertions.assertEquals(200, putResponse.code());
    }

    @Test
    public void testDeleteRecipe() throws IOException {
        Request request = new Request.Builder().url(BASE_URL + "/recipes/2")
                .addHeader("Authorization", "Bearer" + token).delete()
                .build();
        Response response = client.newCall(request).execute();
        Assertions.assertEquals(200, response.code(), () -> "Recipe should delete successfully");
        Request getRequest = new Request.Builder().url(BASE_URL + "/recipes/2").get().addHeader("Authorization", token)
                .build();
        Response getResponse = client.newCall(getRequest).execute();
        Assertions.assertEquals(404, getResponse.code(), () -> "After Delection, Recipe Should Not Be Found");
    }

    @Test
    public void testFilteredPageOfRecipes() throws IOException {
        List<Recipe> filteredResult = List.of(recipeList.get(2));
        Page<Recipe> filteredResultPage = new Page<Recipe>(2, 1, 2, 2, filteredResult);
        String filteredResultJSON = new JavalinJackson().toJsonString(filteredResultPage, Page.class);
        Request request = new Request.Builder()
                .url(BASE_URL + "/recipes?term=ato&page=2&pageSize=1&sortBy=name&sortDirection=asc").get()
                .addHeader("Authorization", token).build();
        Response response = client.newCall(request).execute();
        Assertions.assertEquals(filteredResultJSON, response.body().string(), () -> "Filtered Page Should Return Correct Results");
    }
}
