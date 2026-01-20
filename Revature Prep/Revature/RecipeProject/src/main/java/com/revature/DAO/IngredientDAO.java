package com.revature.DAO;

import com.revature.Model.Ingredient;
import com.revature.Util.ConnectionUtil;
import com.revature.Util.Page;
import com.revature.Util.PageOptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class IngredientDAO {
    private ConnectionUtil connectionUtil;

    public IngredientDAO(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    public Ingredient getIngredientById(int id) {
        String sql = "SELECT * FROM ingredient WHERE id = ?";

        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ? mapSingleRow(resultSet) : null;
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return null;
    }

    public int createIngredient(Ingredient ingredient) {
        String sql = "INSERT INTO INGREDIENT (name) VALUES (?)";

        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ingredient.getName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new RuntimeException("Unable to Create Ingredient");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return 0;
    }

    public void deleteIngredient(Ingredient ingredient) {
        String deleteRecipeIngredientSql = "DELETE FROM RECIPE_INGREDIENT WHERE ingredient_id = ?";
        String deleteIngredientSql = "DELETE FROM INGREDIENT WHERE id = ?";

        Connection connection = connectionUtil.getConnection();

        try {
            connection.setAutoCommit(false);

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(deleteRecipeIngredientSql);
                preparedStatement.setInt(1, ingredient.getId());;
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
            }

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(deleteIngredientSql);
                preparedStatement.setInt(1, ingredient.getId());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new RuntimeException("No Ingredient Found with ID: " + ingredient.getId());
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("SQL Exception: " + rollbackEx.getMessage());
            }
        }
    }

    public void updateIngredient(Ingredient ingredient) {
        String sql = "UPDATE INGREDIENT SET name = ? WHERE id = ?";

        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ingredient.getName());
            preparedStatement.setInt(2, ingredient.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public List<Ingredient> getAllIngredients() {
        String sql = "SELECT * FROM INGREDIENT ORDER BY id";

        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            return mapRows(resultSet);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return null;
    }

    public Page<Ingredient> getAllIngredients(PageOptions pageOptions) {
        String sql = String.format("SELECT * FROM INGREDIENT ORDER BY %s %s", pageOptions.getSortBy(), pageOptions.getSortDirection());

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

    public List<Ingredient> searchIngredients(String term) {
        String sql = "SELECT * FROM INGREDIENT WHERE name LIKE ? ORDER BY id";

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

    public Page<Ingredient> searchIngredients(String term, PageOptions pageOptions) {
        String sql = String.format("SELECT * FROM INGREDIENT WHERE name LIKE ? ORDER BY %s %s", pageOptions.getSortBy(), pageOptions.getSortDirection());

        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + term + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            return pageResults(resultSet, pageOptions);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to Search Ingredients by Term", e);
        }
    }

    /**
     * Maps a single row from the ResultSet to an Ingredient object.
     *
     * @param resultSet The ResultSet containing Ingredient data
     * @return An Ingredient object containing the data from the ResultSet
     * @throws SQLException If an error occurs while reading from the ResultSet
     */
    private Ingredient mapSingleRow(ResultSet resultSet) throws SQLException {
        return new Ingredient(resultSet.getInt("id"), resultSet.getString("name"));
    }

    /**
     * Maps multiple rows from the ResultSet to Ingredient objects.
     *
     * @param resultSet The ResultSet containing Ingredient data
     * @return A List of Ingredient objects containing the data from the ResultSet
     * @throws SQLException If an error occurs while reading from the ResultSet
     */
    private List<Ingredient> mapRows(ResultSet resultSet) throws SQLException {
        List<Ingredient> ingredients = new java.util.ArrayList<>();
        while (resultSet.next()) {
            ingredients.add(mapSingleRow(resultSet));
        }
        return ingredients;
    }

    /**
     * Paginates the results of a ResultSet and maps them to Ingredient objects.
     *
     * @param resultSet The ResultSet containing Ingredient data
     * @param pageOptions Options for pagination and sorting
     * @return A Page of Ingredient objects containing the paginated results
     * @throws SQLException If an error occurs while reading from the ResultSet
     */
    private Page<Ingredient> pageResults(ResultSet resultSet, PageOptions pageOptions) throws SQLException {
        List<Ingredient> ingredients = mapRows(resultSet);
        int offset = (pageOptions.getPageNumber() - 1) * pageOptions.getPageSize();
        int limit = offset + pageOptions.getPageSize();
        List<Ingredient> subList = ingredients.subList(offset, limit);

        return new Page<>(pageOptions.getPageNumber(), pageOptions.getPageSize(), (int) Math.ceil(ingredients.size() / ((float) pageOptions.getPageSize())), ingredients.size(), subList);
    }
}
