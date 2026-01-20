package com.revature.DAO;

import com.revature.Model.Chef;
import com.revature.Model.Recipe;
import com.revature.Util.ConnectionUtil;
import com.revature.Util.Page;
import com.revature.Util.PageOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RecipeDAOTest {
    @Mock
    private ConnectionUtil connectionUtil;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private ChefDAO chefDAO;

    @InjectMocks
    private RecipeDAO recipeDAO = new RecipeDAO(chefDAO, null, null);

    private List<Recipe> recipeList;
    private List<Chef> chefList;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        chefList = Arrays.asList(
                new Chef(1, "JoeCool", "snoopy@null.com", "redbarron", false),
                new Chef(2, "CharlieBrown", "goodgrief@peanuts.com", "thegreatpumpkin", false));

        recipeList = Arrays.asList(
                new Recipe(1, "carrot soup", "Put carrot in water. Boil. Maybe salt.", chefList.get(0)),
                new Recipe(2, "potato soup", "Put potato in water. Boil. Maybe salt.", chefList.get(1)));

        when(connectionUtil.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
    }

    @Test
    public void getRecipeById_Success() throws SQLException {
        Recipe expectedRecipe = recipeList.get(0);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(expectedRecipe.getId());
        when(resultSet.getString("name")).thenReturn(expectedRecipe.getName());
        when(resultSet.getString("instructions")).thenReturn(expectedRecipe.getInstructions());
        when(resultSet.getInt("chef_id")).thenReturn(expectedRecipe.getChef().getId());
        when(chefDAO.getChefById(anyInt())).thenReturn(expectedRecipe.getChef());

        Recipe actualRecipe = recipeDAO.getRecipeById(1);

        Assertions.assertEquals(expectedRecipe, actualRecipe);

        verify(preparedStatement).setInt(1, 1);
    }

    @Test
    public void getAllRecipes_Success() throws SQLException {
        String expectedSQL = "SELECT * FROM RECIPE ORDER BY id";
        when(connectionUtil.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery(expectedSQL)).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("name")).thenReturn("carrot soup", "potato soup");
        when(resultSet.getString("instructions"))
                .thenReturn("Put carrot in water. Boil. Maybe salt.",
                        "Put potato in water. Boil. Maybe salt.");
        when(resultSet.getInt("chef_id")).thenReturn(1, 2);

        when(chefDAO.getChefById(1)).thenReturn(chefList.get(0));
        when(chefDAO.getChefById(2)).thenReturn(chefList.get(1));

        List<Recipe> actualRecipes = recipeDAO.getAllRecipes();

        Assertions.assertEquals(recipeList, actualRecipes);
        verify(connection).createStatement();
        verify(preparedStatement).executeQuery(expectedSQL);
        verify(resultSet, times(3)).next();
    }

    @Test
    public void createRecipe_Success() throws SQLException {
        Recipe recipeToCreate = new Recipe(0, "test recipe", "test instructions", chefList.get(0));
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(3);

        int newId = recipeDAO.createRecipe(recipeToCreate);

        Assertions.assertEquals(3, newId);
        verify(preparedStatement).setString(1, recipeToCreate.getName());
        verify(preparedStatement).setString(2, recipeToCreate.getInstructions());
        verify(preparedStatement).setInt(3, recipeToCreate.getChef().getId());
    }

    @Test
    public void updateRecipe_Success() throws SQLException {
        Recipe recipeToUpdate = recipeList.get(0);
        recipeToUpdate.setName("updated name");
        when(preparedStatement.executeUpdate()).thenReturn(1);

        recipeDAO.updateRecipe(recipeToUpdate);

        verify(preparedStatement).setString(1, recipeToUpdate.getInstructions());
        verify(preparedStatement).setInt(2, recipeToUpdate.getChef().getId());
        verify(preparedStatement).setInt(3, recipeToUpdate.getId());
    }

    @Test
    public void deleteRecipe_Success() throws SQLException {
        Recipe recipeToDelete = recipeList.get(0);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        recipeDAO.deleteRecipe(recipeToDelete);

        verify(preparedStatement, times(2)).setInt(1, recipeToDelete.getId());
        verify(preparedStatement, times(2)).executeUpdate();
    }

    @Test
    public void searchRecipesByTerm_Success() throws SQLException {
        String searchTerm = "soup";
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("name")).thenReturn("carrot soup", "potato soup");
        when(resultSet.getString("instructions"))
                .thenReturn("Put carrot in water. Boil. Maybe salt.",
                        "Put potato in water. Boil. Maybe salt.");
        when(resultSet.getInt("author_id")).thenReturn(1, 2);
        when(chefDAO.getChefById(1)).thenReturn(chefList.get(0));
        when(chefDAO.getChefById(2)).thenReturn(chefList.get(1));

        List<Recipe> results = recipeDAO.searchRecipesByTerm(searchTerm);

        Assertions.assertEquals(recipeList, results);

        Assertions.assertEquals(recipeList, results);
        verify(preparedStatement).setString(1, "%" + searchTerm + "%");
    }

    @Test
    public void getAllRecipesPaged_Success() throws SQLException {
        PageOptions pageable = new PageOptions(1, 2);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("name")).thenReturn("carrot soup", "potato soup");
        when(resultSet.getString("instructions"))
                .thenReturn("Put carrot in water. Boil. Maybe salt.",
                        "Put potato in water. Boil. Maybe salt.");
        when(resultSet.getInt("chef_id")).thenReturn(1, 2);
        when(chefDAO.getChefById(1)).thenReturn(chefList.get(0));
        when(chefDAO.getChefById(2)).thenReturn(chefList.get(1));

        Page<Recipe> recipePage = recipeDAO.getAllRecipes(pageable);

        Assertions.assertEquals(2, recipePage.getItems().size());
        Assertions.assertEquals(2, recipePage.getPageSize());
    }
}
