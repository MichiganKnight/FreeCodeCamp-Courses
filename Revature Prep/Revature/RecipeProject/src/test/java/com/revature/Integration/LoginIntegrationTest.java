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
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import okhttp3.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoginIntegrationTest {
    private static int PORT = 8085;
    private static String BASE_URL = "http://localhost:" + PORT;

    private RecipeDAO recipeDAO;
    private RecipeService recipeService;
    private RecipeController recipeController;
    private ChefDAO chefDAO;
    private ChefService chefService;
    private AuthenticationService authService;
    private AuthenticationController authController;
    private IngredientDAO ingredientDAO;
    private IngredientService ingredientService;
    private IngredientController ingredientController;
    private JavalinAppUtil appUtil;
    private Javalin app;
    private OkHttpClient client;

    @BeforeEach
    void setUpTestsData() throws SQLException {
        DBUtil.RUN_SQL();
        chefDAO = new ChefDAO(new ConnectionUtil());
        ingredientDAO = new IngredientDAO(new ConnectionUtil());
        recipeDAO = new RecipeDAO(chefDAO, ingredientDAO, new ConnectionUtil());
        recipeService = new RecipeService(recipeDAO);
        ingredientService = new IngredientService(ingredientDAO);
        chefService = new ChefService(chefDAO);
        authService = new AuthenticationService(chefService);
        authController = new AuthenticationController(chefService, authService);
        recipeController = new RecipeController(recipeService, authService);
        ingredientController = new IngredientController(ingredientService);
        appUtil = new JavalinAppUtil(recipeController, authController, ingredientController);
        app = appUtil.getApp();
        app.start(PORT);
        client = new OkHttpClient();
    }

    @AfterEach
    void tearDownTestsData() {
        app.stop();
    }

    @Test
    public void testSuccessfulLogin() throws IOException {
        Chef chef = new Chef(1, "JoeCool", "redbarron", "snoopy@null.com", false);
        Recipe newRecipe = new Recipe(6, "fried fish", "fish, oil, stove", chef);

        RequestBody chefBody = RequestBody.create(
                "{\"username\":\"" + chef.getUsername() + "\",\"password\":\"" + chef.getPassword() + "\"}",
                MediaType.get("application/json; charset=utf-8"));

        Request loginRequest = new Request.Builder().url(BASE_URL + "/login").post(chefBody).build();
        Response loginResponse = client.newCall(loginRequest).execute();

        String responseBodyString = loginResponse.body() != null ? loginResponse.body().string() : null;
        Assertions.assertEquals(200, loginResponse.code(), "Login Failed: " + responseBodyString);

        String token = responseBodyString;

        RequestBody recipeBody = RequestBody.create(new JavalinJackson().toJsonString(newRecipe, Recipe.class),
                MediaType.get("application/json; charset=utf-8"));

        Request recipeRequest = new Request.Builder()
                .url("http://localhost:8085/recipes")
                .addHeader("Authorization", "Bearer " + token)
                .post(recipeBody)
                .build();

        Response postResponse = client.newCall(recipeRequest).execute();
        Assertions.assertEquals(201, postResponse.code(), "Expected: 201, Actual: " + postResponse.code() + ", Response: " + (postResponse.body() != null ? postResponse.body().string() + "hello" : "null"));

        Request getRequest = new Request.Builder()
                .url(BASE_URL + "/recipes/6")
                .addHeader("Authorization", "Bearer " + token)
                .get()
                .build();

        Response getResponse = client.newCall(getRequest).execute();
        String rBody = getResponse.body().string();

        Assertions.assertEquals(200, getResponse.code(), "Expected: 200, Actual: " + getResponse.code());
        Assertions.assertEquals(new JavalinJackson().toJsonString(newRecipe, Recipe.class), rBody, "Newly Created Recipe Should Return as JSON");
    }

    @Test
    public void testUnsuccessfulLogin() throws IOException {
        Chef chef = new Chef(1, "JoeCool", "woodstock", "snoopy@null.com", false);
        Recipe newRecipe = new Recipe(6, "fried fish", "fish, oil, stove", chef);
        RequestBody chefBody = RequestBody.create(
                "{\"username\": \"" + chef.getUsername() + "\", \"password\": \"" + chef.getPassword() + "\"}",
                MediaType.get("application/json; charset=utf-8"));
        Request loginRequest = new Request.Builder().url(BASE_URL + "/login").post(chefBody).build();
        Response loginResponse = client.newCall(loginRequest).execute();
        String token = loginResponse.body().string();
        RequestBody recipeBody = RequestBody.create(new JavalinJackson().toJsonString(newRecipe, Recipe.class),
                MediaType.get("application/json; charset=utf-8"));
        Request recipeRequest = new Request.Builder().url("http://localhost:8085/recipes")
                .addHeader("Authorization", token).post(recipeBody).build();
        Response postResponse = client.newCall(recipeRequest).execute();
        Assertions.assertEquals(401, postResponse.code(), "Expected: 401, Actual: " + postResponse.code());
        Assertions.assertTrue(List.of("", "Invalid Credentials", "Invalid Username or Password").contains(token));
        Assertions.assertEquals(401, postResponse.code());
    }

    @Test
    public void testLogout() throws IOException {
        Chef chef = new Chef(1, "JoeCool", "redbarron", "snoopy@null.com", false);
        Recipe newRecipe = new Recipe(6, "fried fish", "fish, oil, stove", chef);
        RequestBody chefBody = RequestBody.create(
                "{\"username\":\"" + chef.getUsername() + "\", \"password\":\"" + chef.getPassword() + "\"}",
                MediaType.get("application/json; charset=utf-8"));
        Request loginRequest = new Request.Builder().url(BASE_URL + "/login").post(chefBody).build();
        Response loginResponse = client.newCall(loginRequest).execute();
        String token = loginResponse.body().string();
        Request logoutRequest = new Request.Builder().url(BASE_URL + "/logout").post(chefBody)
                .addHeader("Authorization", "Bearer " + token).build();
        Response logoutResponse = client.newCall(logoutRequest).execute();
        RequestBody recipeBody = RequestBody.create(new JavalinJackson().toJsonString(newRecipe, Recipe.class),
                MediaType.get("application/json; charset=utf-8"));

        Request recipeRequest = new Request.Builder().url("http://localhost:8085/recipes")
                .addHeader("Authorization", "Bearer " + token).post(recipeBody).build();
        Response postResponse = client.newCall(recipeRequest).execute();
        Request getRequest = new Request.Builder().url(BASE_URL + "/recipes/6").get().build();
        Response getResponse = client.newCall(getRequest).execute();
        Assertions.assertEquals(200, loginResponse.code(), "Login Should Return a Success Code. Expected: 200, Actual: " + loginResponse.code());
        Assertions.assertEquals(200, logoutResponse.code(), () -> "Logout Should Be Successful");
        Assertions.assertEquals(401, postResponse.code(), postResponse.body().string());
        Assertions.assertEquals(404, getResponse.code(), () -> "Recipe Should Not Have Been Created");
    }

    @Test
    public void testRegister() throws IOException {
        Chef chef = new Chef(0, "new chef", "1234abc", "newchef@chefmail.com", false);
        RequestBody chefBody = RequestBody.create(
                "{\"username\": \"" + chef.getUsername() + "\", \"password\": \" " + chef.getPassword()
                        + "\", \"email\": \"" + chef.getEmail() + "\"}",
                MediaType.get("application/json; charset=utf-8"));
        Request registerRequest = new Request.Builder().url(BASE_URL + "/register").post(chefBody).build();
        Response registerResponse = client.newCall(registerRequest).execute();

        Assertions.assertEquals(201, registerResponse.code(), () -> "Should Successfully Register User");

        RequestBody loginBody = RequestBody.create(
                "{\"username\": \"" + chef.getUsername() + "\", \"password\": \" " + chef.getPassword() + "\"}",
                MediaType.get("application/json; charset=utf-8"));
        Request loginRequest = new Request.Builder().url(BASE_URL + "/login").post(loginBody).build();
        Response loginResponse = client.newCall(loginRequest).execute();

        Assertions.assertEquals(200, loginResponse.code(), () -> "Login Should Return a Success Code. Expected: 200, Actual: " + loginResponse.code());
        Assertions.assertNotNull(loginResponse.body().string(), () -> "Login Response Body Should Not Be Null");
    }
}
