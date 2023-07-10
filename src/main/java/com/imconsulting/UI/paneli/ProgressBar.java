package com.imconsulting.UI.paneli;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
public class ProgressBar extends Application {
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        StackPane root = new StackPane();
        javafx.scene.control.ProgressBar progress = new javafx.scene.control.ProgressBar();
        root.getChildren().add(progress);
        Scene scene = new Scene(root,300,200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Progress Bar Example");
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}