package com.revature.weekThree.sql;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Toast {
    public static void ShowToast(Stage parentStage, String message, double seconds, String cssFile, boolean status) {
        Stage toastStage = new Stage();
        toastStage.initOwner(parentStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);
        toastStage.initModality(Modality.NONE);

        Label label = new Label(message);

        StackPane root = new StackPane(label);
        root.setStyle("-fx-background-color: transparent;");
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, Color.TRANSPARENT);
        scene.getStylesheets().add(cssFile);
        label.getStyleClass().add("toast-label");

        if (status) {
            label.setStyle("-fx-background-color: green;");
        } else {
            label.setStyle("-fx-background-color: maroon;");
        }

        toastStage.setScene(scene);

        PauseTransition delay = new PauseTransition(Duration.seconds(seconds));
        delay.setOnFinished(e -> toastStage.hide());

        toastStage.show();

        toastStage.setX(parentStage.getX() + parentStage.getWidth() / 2 - label.prefWidth(-1) / 2);
        toastStage.setY(parentStage.getY() + parentStage.getHeight() - 100);

        delay.play();
    }
}
