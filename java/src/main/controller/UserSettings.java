package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.model.object.user.User;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserSettings extends AbstractController {
    @FXML
    private Label heading;

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = LoginController.user;
        heading.setText("User Settings For\n" + user.getUsername());
    }

    public void editUser(ActionEvent event) {
        try {
            newScene("editUser.fxml", event);
        } catch (IOException e) {
            heading.setText("Edit User page Not Found");
        }
    }

    public void logout(ActionEvent event) {
        try {
            newScene("login.fxml", event);
        } catch (IOException e) {
            heading.setText("Login Page Not Found");
        }
    }

    public void back(ActionEvent event) {
        //TODO
    }



}
