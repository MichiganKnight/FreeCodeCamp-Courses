package com.revature.weekThree.sql;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SQL extends Application {
    private static final String connectionString = "jdbc:sqlserver://localhost:1433;";
    private final String css = getClass().getResource("/styles/style.css").toExternalForm();

    @Override
    public void start(Stage stage) throws Exception {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new javafx.geometry.Insets(20, 0, 0, 0));

        init(grid);

        Properties prop = new Properties();
        prop.setProperty("integratedSecurity", "true");
        prop.setProperty("trustServerCertificate", "true");
        prop.setProperty("encrypt", "false");

        StackPane root = new StackPane();
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 1280, 720);

        createDatabaseButtons(grid, prop, stage);

        scene.getStylesheets().add(css);

        stage.setTitle("Java SQL");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

        Platform.runLater(stage::requestFocus);
    }

    private static void init(GridPane parentGrid) {
        Text title = new Text("Java SQL");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        title.setTextAlignment(TextAlignment.CENTER);

        parentGrid.add(title, 0, 0, GridPane.REMAINING, 1);

        GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);
    }

    private void createDatabaseButtons(GridPane parentGrid, Properties prop, Stage stage) {
        List<Button> buttons = new ArrayList<>();

        Button createDatabaseBtn = new Button("Create Database");
        Button showDatabasesBtn = new Button("Show Databases");
        Button dropDatabaseBtn = new Button("Drop Database");

        buttons.add(createDatabaseBtn);
        buttons.add(showDatabasesBtn);
        buttons.add(dropDatabaseBtn);

        for (Button button : buttons) {
            button.getStyleClass().add("database-button");
        }

        createDatabaseBtn.setOnAction(e -> {
            try (Connection connection = DriverManager.getConnection(connectionString, prop)) {
                SQLDatabase.createDatabase(connection, css, stage);
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        });

        showDatabasesBtn.setOnAction(e -> {
            try (Connection connection = DriverManager.getConnection(connectionString, prop)) {
                SQLDatabase.showDatabases(connection, css, stage);
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        });

        dropDatabaseBtn.setOnAction(e -> {
            try (Connection connection = DriverManager.getConnection(connectionString, prop)) {
                //SQLDatabase.dropDatabase(connection);
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        });

        parentGrid.add(createDatabaseBtn, 0, 1);
        parentGrid.add(showDatabasesBtn, 1, 1);
        parentGrid.add(dropDatabaseBtn, 2, 1);
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("=== Example Interactions with SQL and SQL Server ===");

        launch(args);
    }
}
