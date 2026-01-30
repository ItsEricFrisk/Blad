package com.itsericfrisk.blad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("view/mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/com/itsericfrisk/blad/css/styles.css")).toExternalForm()
        );
        stage.setTitle("Blad");
        stage.setScene(scene);
        stage.show();
    }
}

