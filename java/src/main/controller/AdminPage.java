package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class AdminPage extends EmployeePage {
    @FXML
    private Label usernameLabel;

    /**
     * Takes the user to the page with administration functions
     * @param actionEvent
     */
    public void management(ActionEvent actionEvent) {
        try {
            newScene("management.fxml", actionEvent);
        } catch (IOException e) {
            usernameLabel.setText("Management Page Unavailable");
        }
    }
}
