package com.revature.DAO;

import com.revature.Model.Chef;
import com.revature.Util.ConnectionUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChefDAOTest {
    @Mock
    private ConnectionUtil connectionUtil;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ChefDAO chefDAO = new ChefDAO(new ConnectionUtil());

    private Chef testChef;

    private AutoCloseable openMocks;

    @BeforeEach
    public void setUp() throws Exception {
        openMocks = MockitoAnnotations.openMocks(this);

        when(connectionUtil.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        testChef = new Chef(1, "testChef", "password123", "chef@chef.com", false);
    }

    @AfterEach
    public void tearDownTests() throws Exception {
        openMocks.close();
    }

    @Test
    public void testGetAllChefs() throws Exception {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("username")).thenReturn("chef1", "chef2");
        when(resultSet.getString("email")).thenReturn("chef1@test.com", "chef2@test.com");
        when(resultSet.getString("password")).thenReturn("pass1", "pass2");
        when(resultSet.getBoolean("is_admin")).thenReturn(false, false);

        List<Chef> chefs = chefDAO.getAllChefs();

        Assertions.assertNotNull(chefs);
        Assertions.assertEquals(2, chefs.size());
        Assertions.assertEquals("chef1", chefs.get(0).getUsername());
        Assertions.assertEquals("chef2", chefs.get(1).getUsername());

        verify(statement).executeQuery("SELECT * FROM CHEF ORDER BY id");
    }

    @Test
    public void testGetChefById() throws Exception {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("username")).thenReturn("testChef");
        when(resultSet.getString("email")).thenReturn("test@chef.com");
        when(resultSet.getString("password")).thenReturn("password123");
        when(resultSet.getBoolean("is_admin")).thenReturn(false);

        Chef chef = chefDAO.getChefById(1);

        Assertions.assertNotNull(chef);
        Assertions.assertEquals(1, chef.getId());
        Assertions.assertEquals("testChef", chef.getUsername());
        Assertions.assertEquals("test@chef.com", chef.getEmail());

        verify(preparedStatement).setInt(1, 1);
    }

    @Test
    public void testCreateChef() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        int newId = chefDAO.createChef(testChef);

        Assertions.assertEquals(1, newId);
        verify(preparedStatement).setString(1, testChef.getUsername());
        verify(preparedStatement).setString(2, testChef.getPassword());
        verify(preparedStatement).setString(3, testChef.getEmail());
        verify(preparedStatement).setBoolean(4, testChef.isAdmin());
    }

    @Test
    public void testUpdateChef() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        chefDAO.updateChef(testChef);

        verify(preparedStatement).setString(1, testChef.getUsername());
        verify(preparedStatement).setString(2, testChef.getPassword());
        verify(preparedStatement).setString(3, testChef.getEmail());
        verify(preparedStatement).setBoolean(4, testChef.isAdmin());
        verify(preparedStatement).setInt(5, testChef.getId());
    }

    @Test
    public void testDeleteChef() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        chefDAO.deleteChef(testChef);

        verify(preparedStatement).setInt(1, testChef.getId());
        verify(preparedStatement).executeUpdate();
    }
}
