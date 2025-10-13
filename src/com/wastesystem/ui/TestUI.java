package com.wastesystem.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TestUI extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("JavaFX is working!");
        stage.setScene(new Scene(label, 300, 150));
        stage.setTitle("UI Test");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}



// Handles the Human-Machine Interface (HMI) — later via JavaFX. For now, we’ll start with basic console-based output; later upgrade to GUI.