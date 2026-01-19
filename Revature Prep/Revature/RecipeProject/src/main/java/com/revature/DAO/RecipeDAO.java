package com.revature.DAO;

import com.revature.Model.Chef;
import com.revature.Model.Recipe;
import com.revature.Util.ConnectionUtil;
import com.revature.Util.Page;
import com.revature.Util.PageOptions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO {
    private ChefDAO chefDAO;
    private IngredientDAO ingredientDAO;
    private ConnectionUtil connectionUtil;

    public RecipeDAO(ChefDAO chefDAO, IngredientDAO ingredientDAO, ConnectionUtil connectionUtil) {
        this.chefDAO = chefDAO;
        this.ingredientDAO = ingredientDAO;
        this.connectionUtil = connectionUtil;
    }
    
    public List<Recipe> getAllRecipes() {
        try {
            Connection connection = connectionUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM RECIPE ORDER BY id";
            ResultSet resultSet = statement.executeQuery(sql);
            
            return mapRows(resultSet);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        
        return null;
    }
    
    public Page<Recipe> getAllRecipes(PageOptions pageOptions) {
        String sql = String.format("SELECT * FROM RECIPE ORDER BY %s %s", pageOptions.getSortBy(), pageOptions.getSortDirection());
        
        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            return pageResults(resultSet, pageOptions);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<Recipe> searchRecipesByTerm(String term) {
        String sql = "SELECT * FROM RECIPE WHERE name LIKE ?";
        
        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + term + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            
            return mapRows(resultSet);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<Recipe> searchRecipesByIngredient(String ingredientName) {
        String sql = "SELECT r.id, r.name, r.instructions FROM RECIPE r JOIN RECIPE_INGREDIENT ri ON r.id = ri.recipe_id JOIN INGREDIENT i ON ri.ingredient_id = i.id WHERE i.name LIKE ?;";
        
        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + ingredientName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            
            return mapRows(resultSet);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        
        return null;
    }
    
    public Page<Recipe> searchRecipesByTerm(String term, PageOptions pageOptions) {
        String sql = String.format("SELECT * FROM RECIPE WHERE name LIKE ? ORDER BY %s %s", pageOptions.getSortBy(), pageOptions.getSortDirection());
        
        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + term + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            
            return pageResults(resultSet, pageOptions);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        
        return null;
    }
    
    public Recipe getRecipeById(int id) {
        String sql = "SELECT * FROM RECIPE WHERE id = ?";
        
        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();            
            if (resultSet.next()) {
                return mapSingleRow(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        
        return null;
    }
    
    public int createRecipe(Recipe recipe) {
        String sql = "INSERT INTO RECIPE (name, instructions, chef_id) VALUES (?, ?, ?)";
        int generatedId = 0;
        
        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, recipe.getName());
            preparedStatement.setString(2, recipe.getInstructions());
            preparedStatement.setInt(3, recipe.getChef().getId());
            preparedStatement.executeUpdate();

            try {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return generatedId;
    }

    public void updateRecipe(Recipe recipe) {
        String sql = "UPDATE RECIPE SET instructions = ?, chef_id = ? WHERE id = ?";

        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, recipe.getInstructions());
            preparedStatement.setInt(2, recipe.getChef().getId());
            preparedStatement.setInt(3, recipe.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public void deleteRecipe(Recipe recipe) {
        String ingredientSql = "DELETE FROM RECIPE_INGREDIENT WHERE recipe_id = ?";
        String sql = "DELETE FROM RECIPE WHERE id = ?";

        try {
            Connection connection = connectionUtil.getConnection();
            connection.setAutoCommit(false);

            try {
                PreparedStatement ingredientStatement = connection.prepareStatement(ingredientSql);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ingredientStatement.setInt(1, recipe.getId());
                ingredientStatement.executeUpdate();
                preparedStatement.setInt(1, recipe.getId());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Unable to Delete Recipe" + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    /**
     * Maps a single row from the ResultSet to a Recipe object.
     *
     * @param resultSet The ResultSet containing Recipe data
     * @return A Recipe object containing the data from the ResultSet
     * @throws SQLException If an error occurs while reading from the ResultSet
     */
    private Recipe mapSingleRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String instructions = resultSet.getString("instructions");
        Chef chef = chefDAO.getChefById(resultSet.getInt("chef_id"));

        return new Recipe(id, name, instructions, chef);
    }

    /**
     * Maps multiple rows from the ResultSet to Recipe objects.
     *
     * @param resultSet The ResultSet containing Recipe data
     * @return A List of Recipe objects containing the data from the ResultSet
     * @throws SQLException If an error occurs while reading from the ResultSet
     */
    private List<Recipe> mapRows(ResultSet resultSet) throws SQLException {
        List<Recipe> recipes = new java.util.ArrayList<>();
        while (resultSet.next()) {
            recipes.add(mapSingleRow(resultSet));
        }
        return recipes;
    }

    /**
     * Paginates the results of a ResultSet and maps them to Recipe objects.
     *
     * @param resultSet The ResultSet containing Recipe data
     * @param pageOptions Options for pagination and sorting
     * @return A Page of Recipe objects containing the paginated results
     * @throws SQLException If an error occurs while reading from the ResultSet
     */
    private Page<Recipe> pageResults(ResultSet resultSet, PageOptions pageOptions) throws SQLException {
        List<Recipe> recipes = mapRows(resultSet);
        int offset = (pageOptions.getPageNumber() - 1) * pageOptions.getPageSize();
        int limit = offset + pageOptions.getPageSize();
        List<Recipe> slicedList = sliceList(recipes, offset, limit);

        return new Page<>(pageOptions.getPageNumber(), pageOptions.getPageSize(), recipes.size() / pageOptions.getPageSize(), recipes.size(), slicedList);
    }

    /**
     * Slices a list of Recipe objects from a starting index to an ending index.
     *
     * @param list The list of Recipe objects to slice
     * @param start The index to start slicing from
     * @param end The index to end slicing at
     * @return A new list containing the sliced Recipe objects
     */
    private List<Recipe> sliceList(List<Recipe> list, int start, int end) {
        List<Recipe> slicedList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            slicedList.add(list.get(i));
        }
        return slicedList;
    }
}
