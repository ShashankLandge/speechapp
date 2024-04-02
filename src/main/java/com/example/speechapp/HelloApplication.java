package com.example.speechapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main_scene.fxml"));
        Scene main_scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("SpeechApp");
        stage.setScene(main_scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}