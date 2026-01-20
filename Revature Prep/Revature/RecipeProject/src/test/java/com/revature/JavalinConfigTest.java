package com.revature;

import com.revature.Controller.AuthenticationController;
import com.revature.Controller.IngredientController;
import com.revature.Controller.RecipeController;
import com.revature.DAO.ChefDAO;
import com.revature.DAO.IngredientDAO;
import com.revature.DAO.RecipeDAO;
import com.revature.Service.AuthenticationService;
import com.revature.Service.ChefService;
import com.revature.Service.IngredientService;
import com.revature.Service.RecipeService;
import com.revature.Util.ConnectionUtil;
import com.revature.Util.JavalinAppUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class JavalinConfigTest {
    private ChefDAO chefDAO;
    private IngredientDAO ingredientDAO;
    private RecipeDAO recipeDAO;
    private ChefService chefService;
    private IngredientService ingredientService;
    private RecipeService recipeService;
    private AuthenticationService authenticationService;
    private AuthenticationController authController;
    private IngredientController ingredientController;
    private RecipeController recipeController;

    @BeforeEach
    void setUpTestsData() throws SQLException {
        chefDAO = new ChefDAO(new ConnectionUtil());
        chefService = new ChefService(chefDAO);

        authenticationService = new AuthenticationService(chefService);
        authController = new AuthenticationController(chefService, authenticationService);

        ingredientDAO = new IngredientDAO(new ConnectionUtil());
        ingredientService = new IngredientService(ingredientDAO);
        ingredientController = new IngredientController(ingredientService);

        recipeDAO = new RecipeDAO(chefDAO, ingredientDAO, new ConnectionUtil());
        recipeService = new RecipeService(recipeDAO);
        recipeController = new RecipeController(recipeService, authenticationService);
    }

    @Test
    public void test() {
        new JavalinAppUtil(recipeController, authController, ingredientController).getApp().start();
    }
}
