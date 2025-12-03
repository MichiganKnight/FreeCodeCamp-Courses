package com.revature.weekThree.sql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class SQLDatabase {
    public static class DatabaseEntry {
        private final String name;

        public DatabaseEntry(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static void createDatabase(Connection connection, String css, Stage stage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Database");
        dialog.setHeaderText("Enter New Database Name");
        dialog.setContentText("Name: ");

        dialog.setGraphic(null);

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().clear();
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, cancelButtonType);

        dialog.getDialogPane().getScene().getStylesheets().add(css);
        dialog.getDialogPane().lookupButton(createButtonType).getStyleClass().add("database-button");
        dialog.getDialogPane().lookupButton(cancelButtonType).getStyleClass().add("database-button");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() && !result.get().trim().isEmpty()) {
            String databaseName = result.get().trim();

            try (Statement statement = connection.createStatement()) {
                String sql = "IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = '" + databaseName + "') " +
                        "BEGIN " +
                        "    CREATE DATABASE [" + databaseName + "]; " +
                        "END;";

                int updateCount = statement.executeUpdate(sql);

                System.out.println(updateCount);

                if (updateCount >= 0) {
                    Toast.ShowToast(stage, "Database: [" + databaseName + "] Created Successfully", 3.5, css, true);
                } else {
                    Toast.ShowToast(stage, "Error: Database [" + databaseName + "] Already Exists", 3.5, css, false);
                }

            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());

                Toast.ShowToast(stage, "Error: Failed to Create Database (" + ex.getErrorCode() + ")", 3.5, css, false);
            }
        }
    }

    public static void showDatabases(Connection connection, String css, Stage parentStage) {
        ObservableList<DatabaseEntry> databases = FXCollections.observableArrayList();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT name FROM sys.databases WHERE name NOT IN ('master', 'tempdb', 'model', 'msdb')"
            );

            while (resultSet.next()) {
                databases.add(new DatabaseEntry(resultSet.getString("name")));
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());

            return;
        }

        Stage stage = new Stage();
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Available Databases");

        TableView<DatabaseEntry> tableView = new TableView<>(databases);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<DatabaseEntry, String> nameColumn = new TableColumn<>("Database");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setStyle("-fx-alignment: CENTER;");

        Button modifyButton = new Button("✎");
        modifyButton.getStyleClass().add("database-button");

        TableColumn<DatabaseEntry, Void> actionsColumn = getActionsColumn(connection, parentStage, css);

        tableView.getColumns().addAll(nameColumn, actionsColumn);

        VBox root = new VBox(tableView);
        root.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    private static TableColumn<DatabaseEntry, Void> getActionsColumn(Connection connection, Stage stage, String css) {
        TableColumn<DatabaseEntry, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setStyle("-fx-alignment: CENTER;");
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            final Button modifyButton = new Button("✎");
            final Button deleteButton = new Button("🗑");
            final HBox pane = new HBox(5, modifyButton, deleteButton); // HBox for buttons

            {
                // Action handlers for buttons
                modifyButton.setOnAction(event -> {
                    DatabaseEntry entry = getTableView().getItems().get(getIndex());
                    handleModifyAction(entry.getName());
                });

                deleteButton.setOnAction(event -> {
                    DatabaseEntry entry = getTableView().getItems().get(getIndex());
                    handleDeleteAction(entry.getName(), connection, stage, css);
                });

                // Apply style class if needed (ensure 'database-button' is defined in your CSS)
                modifyButton.getStyleClass().add("database-button");
                deleteButton.getStyleClass().add("database-button");
                pane.setPadding(new Insets(0, 0, 0, 10));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane); // Set the HBox with buttons as the cell graphic
                }
            }
        });
        return actionsColumn;
    }

    // Placeholder methods for button actions
    private static void handleModifyAction(String dbName) {
        System.out.println("Modify action for database: " + dbName);
        // Implement your modify logic here (e.g., open a new dialog)
    }

    private static void handleDeleteAction(String dbName, Connection connection, Stage stage, String css) {
        dropDatabase(connection, dbName,  stage, css);
    }

    public static void dropDatabase(Connection connection, String databaseName, Stage stage, String css) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DROP DATABASE " + databaseName;
            statement.executeUpdate(sql);

            Toast.ShowToast(stage, "Database: [" + databaseName + "] Created Successfully", 3.5, css, true);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
}
