package org.yaam.javaprojectv2.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.yaam.javaprojectv2.jdbc.entities.Film;

import java.net.URL;
import java.util.ResourceBundle;

public class ModificationController implements Initializable {
    @FXML
    private TextField titreTextField;
    @FXML
    private TextField realisateurTextField;
    // Autres éléments de votre vue de modification

    private Film film;

    public void setFilm(Film film) {
        this.film = film;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            System.out.println(film);

    }
}
