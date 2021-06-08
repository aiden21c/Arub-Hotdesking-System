package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.controller.singleton.UserSingleton;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserSettings extends AbstractController {
    @FXML
    private Label heading;

    private UserSingleton userSingleton;

    /**
     * Initialises the user settings page with the current logged in user
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userSingleton = UserSingleton.getInstance();
        heading.setText("User Settings For\n" + userSingleton.getUser().getUsername());
    }

    /**
     * Takes the user to the edit user page
     * @param event
     */
    public void editUser(ActionEvent event) {
        try {
            newScene("editUser.fxml", event);
        } catch (IOException e) {
            heading.setText("Edit User page Not Found");
        }
    }

    /**
     * Logs the user out of the system
     * @param event
     */
    public void logout(ActionEvent event) {
        try {
            newScene("login.fxml", event);
        } catch (IOException e) {
            heading.setText("Login Page Not Found");
        }
    }

    /**
     * Takes the user back to the employee page
     * @param event
     */
    public void back(ActionEvent event) {
        try {
            super.back(event);
        } catch (IOException e) {
            heading.setText("User Page Not Found");
        }
    }



}
