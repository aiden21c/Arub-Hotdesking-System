package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.controller.singleton.UserSingleton;
import main.model.object.user.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeePage extends AbstractController {
    @FXML
    private Label usernameLabel;

    private UserSingleton userSingleton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userSingleton = UserSingleton.getInstance();
        usernameLabel.setText(userSingleton.getUser().getUsername().toUpperCase());
    }

    /**
     * Takes the user to their user settings page
     * @param event
     */
    public void user(ActionEvent event) {
        try {
            newScene("user.fxml", event);
        } catch (IOException e) {
            usernameLabel.setText("Cannot find user page");
        }
    }

    /**
     * Takes the user to their current booking page
     * @param event
     */
    public void currentBooking(ActionEvent event) {
        try {
            newScene("currentBooking.fxml", event);
        } catch (IOException e) {
            usernameLabel.setText("Cannot find Booking page");
        }
    }

    /**
     * Takes the user to the create new booking page
     * @param actionEvent
     */
    public void createBooking(ActionEvent actionEvent) {
        // TODO
        System.out.println("createBooking clicked");
    }
}
