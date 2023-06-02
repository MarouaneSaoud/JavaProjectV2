package org.yaam.javaprojectv2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.yaam.javaprojectv2.App;
import org.yaam.javaprojectv2.jdbc.entities.User;
import org.yaam.javaprojectv2.jdbc.service.UserService;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;

    @FXML
    public void initialize(){
        login.setOnMouseClicked(arg->{
           if ( auth(constructionUser())){
               try {

                   Stage stage1 = (Stage) login.getScene().getWindow();
                   stage1.close();
                   Stage stage= new Stage();
                   FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("film-view.fxml"));
                   Scene scene = new Scene(fxmlLoader.load(), 1099 , 660);
                   stage.setResizable(false);
                   stage.setTitle("Film App");
                   stage.setScene(scene);

                   stage.show();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           else {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Information");
               alert.setHeaderText("Auth erreur");
               alert.setContentText("Veuillez saisir les informations d'authentification .");
               alert.showAndWait();

           }
        });

    }

    public boolean auth(User user){
        UserService userService = new UserService();
        return userService.auth(user);
    }
    public User constructionUser(){
        User user = new User();
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Auth erreur");
            alert.setContentText("Veuillez saisir les informations d'authentification .");
            alert.showAndWait();
        }
        else {
            user.setPassword(password.getText());
            user.setUsername(username.getText());
        }
        return user;
    }

}
