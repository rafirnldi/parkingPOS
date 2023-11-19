package com.example.parkingfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
         Scene scene = new Scene(root);
         scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
         stage.setScene(scene);
         stage.setTitle("PARKING POS SYSTEM");
         stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}