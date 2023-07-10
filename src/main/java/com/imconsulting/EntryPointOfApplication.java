package com.imconsulting;

import com.imconsulting.UI.Controller;
import com.imconsulting.UI.paneli.LoginPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class EntryPointOfApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Controller.instance().setMainStage(stage);
        LoginPanel loginPanel = new LoginPanel();
        Scene scene = new Scene(loginPanel);
        stage.setScene(scene);
        stage.setTitle("IM-Consulting");
        stage.show();
        stage.setResizable(false); // nema mogućnost proširivanja screena
        //stage.setHeight(500); //visina
        //stage.setWidth(500); //sirina
        //stage.centerOnScreen();
    }
}