package com.revature.miniProjects.LibraryApp;

import com.revature.miniProjects.LibraryApp.Controller.LibraryController;
import com.revature.miniProjects.LibraryApp.Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        databaseSetup();

        LibraryController libraryController = new LibraryController();
        libraryController.startAPI();
    }

    public static void databaseSetup() {
        try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement ps1 = connection.prepareStatement("DROP TABLE IF EXISTS book");
            ps1.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement("DROP TABLE IF EXISTS author");
            ps2.executeUpdate();

            PreparedStatement ps3 = connection.prepareStatement("create TABLE author(" +
                    "id INT PRIMARY KEY auto_increment, " +
                    "name VARCHAR(255)); ");
            ps3.executeUpdate();

            PreparedStatement ps4 = connection.prepareStatement("create TABLE book(" +
                    "isbn INT PRIMARY KEY, " +
                    "author_id INT, "+
                    "title VARCHAR(255), " +
                    "copies_available INT, " +
                    "FOREIGN KEY (author_id) REFERENCES author(id));");
            ps4.executeUpdate();

            PreparedStatement ps5 = connection.prepareStatement(
                    "INSERT INTO author (name) VALUES " +
                            "('jorge luis borges')," +
                            "('italo calvino')," +
                            "('thomas pynchon')," +
                            "('marshall mcluhan')," +
                            "('immanuel kant')");
            ps5.executeUpdate();

            PreparedStatement ps6 = connection.prepareStatement(
                    "INSERT INTO book (isbn, author_id, title, copies_available) VALUES " +
                            "(100, 1, 'ficciones', 2)," +
                            "(101, 1, 'book of sand', 0)," +
                            "(102, 2, 'mr palomar', 1)," +
                            "(103, 2, 'invisible cities', 3)," +
                            "(104, 3, 'crying of lot 49', 0)," +
                            "(105, 3, 'mason and dixon', 0)," +
                            "(106, 4, 'understanding media', 1)," +
                            "(107, 5, 'critique of pure reason', 7);");
            ps6.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
