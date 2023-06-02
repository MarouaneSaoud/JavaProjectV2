package org.yaam.javaprojectv2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.yaam.javaprojectv2.controller.LoginController;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 604 , 327);
      /*  FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("film-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1099 , 660);*/
        stage.setResizable(false);
        stage.setTitle("Film App");
        stage.setScene(scene);
        stage.show();
    }
}
