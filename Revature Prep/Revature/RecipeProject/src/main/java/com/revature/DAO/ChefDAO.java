package com.revature.DAO;

import com.revature.Model.Chef;
import com.revature.Util.ConnectionUtil;
import com.revature.Util.Page;
import com.revature.Util.PageOptions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChefDAO {
    private ConnectionUtil connectionUtil;

    public ChefDAO(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    public List<Chef> getAllChefs() {
        try {
            Connection connection = connectionUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM CHEF ORDER BY id";
            ResultSet resultSet = statement.executeQuery(sql);

            return mapRows(resultSet);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            return null;
        }
    }

    public Page<Chef> getAllChefs(PageOptions pageOptions) {
        String sql = String.format("SELECT * FROM CHEF ORDER BY %s %s", pageOptions.getSortBy(), pageOptions.getSortDirection());

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

    public Chef getChefById(int id) {
        String sql = "SELECT * FROM CHEF WHERE id = ?";

        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return mapSingleRow(resultSet);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return null;
    }

    public int createChef(Chef chef) {
        String sql = "INSERT INTO CHEF (username, password, email, is_admin) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, chef.getUsername());
            preparedStatement.setString(2, chef.getPassword());
            preparedStatement.setString(3, chef.getEmail());
            preparedStatement.setBoolean(4, chef.isAdmin());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows >= 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return 0;
    }

    public void updateChef(Chef chef) {
        String sql = "UPDATE CHEF SET username = ?, password = ?, email = ?, is_admin = ? WHERE id = ?";

        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, chef.getUsername());
            preparedStatement.setString(2, chef.getPassword());
            preparedStatement.setString(3, chef.getEmail());
            preparedStatement.setBoolean(4, chef.isAdmin());
            preparedStatement.setInt(5, chef.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public void deleteChef(Chef chef) {
        String sql = "DELETE FROM CHEF WHERE id = ?";

        try {
            Connection connection = connectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, chef.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public List<Chef> searchChefsByTerm(String term) {
        String sql = "SELECT * FROM CHEF WHERE username LIKE ?";

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

    public Page<Chef> searchChefsByTerm(String term, PageOptions pageOptions) {
        String sql = String.format("SELECT * FROM CHEF WHERE username LIKE ? ORDER BY %s %s", pageOptions.getSortBy(), pageOptions.getSortDirection());

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

    /**
     * Maps a single row from the ResultSet to a Chef object.
     *
     * @param resultSet The ResultSet containing Chef data
     * @return A Chef object containing the data from the ResultSet
     * @throws SQLException If an error occurs while reading from the ResultSet
     */
    private Chef mapSingleRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String email = resultSet.getString("email");
        boolean isAdmin = resultSet.getBoolean("isAdmin");
        return new Chef(id, username, password, email, isAdmin);
    }

    /**
     * Maps multiple rows from the ResultSet to Chef objects.
     *
     * @param resultSet The ResultSet containing Chef data
     * @return A List of Chef objects containing the data from the ResultSet
     * @throws SQLException If an error occurs while reading from the ResultSet
     */
    private List<Chef> mapRows(ResultSet resultSet) throws SQLException {
        List<Chef> chefs = new ArrayList<>();
        while (resultSet.next()) {
            chefs.add(mapSingleRow(resultSet));
        }
        return chefs;
    }

    /**
     * Paginates the results of a ResultSet and maps them to Chef objects.
     *
     * @param resultSet The ResultSet containing Chef data
     * @param pageOptions Options for pagination and sorting
     * @return Page of Chef objects containing the paginated results
     * @throws SQLException If an error occurs while reading from the ResultSet
     */
    private Page<Chef> pageResults(ResultSet resultSet, PageOptions pageOptions) throws SQLException {
        List<Chef> chefs = mapRows(resultSet);
        int offset = (pageOptions.getPageNumber() - 1) * pageOptions.getPageSize();
        int limit = offset + pageOptions.getPageSize();
        List<Chef> slicedList = sliceList(chefs, offset, limit);
        return new Page<>(pageOptions.getPageNumber(), pageOptions.getPageSize(), chefs.size() / pageOptions.getPageSize(), chefs.size(), slicedList);
    }

    /**
     * Slices a list of Chef objects from a starting index to an ending index.
     * @param list The list of Chef objects to slice
     * @param start The starting index of the slice
     * @param end The ending index of the slice
     * @return The sliced list of Chef objects
     */
    private List<Chef> sliceList(List<Chef> list, int start, int end) {
        List<Chef> slicedList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            slicedList.add(list.get(i));
        }
        return slicedList;
    }
}
