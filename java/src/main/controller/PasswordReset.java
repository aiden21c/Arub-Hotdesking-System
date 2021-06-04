package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import main.controller.singleton.UserSingleton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PasswordReset extends AbstractController {
    @FXML
    private Label newPasswordTitle;
    @FXML
    private PasswordField txtPassword1;
    @FXML
    private PasswordField txtPassword2;
    @FXML
    private Label resetSuccessful;

    private UserSingleton userSingleton;

    /**
     * Sets the scene by initializing the title label text
     *      Assigns the scene's user variable to that captured from the login page
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userSingleton = UserSingleton.getInstance();
        newPasswordTitle.setText("Enter a New Password For " + userSingleton.getUser().getUsername());
    }

    /**
     * Resets the user's password and writes it to the database
     *      Only works if the two password fields match and are not empty
     * @param event
     */
    public void Reset(ActionEvent event) {
        if(!txtPassword1.getText().isEmpty()) {
            if (txtPassword1.getText().equals(txtPassword2.getText())) {
                try {
                    userSingleton.getUser().setNewPassword(txtPassword1.getText());
                    userSingleton.writeToDatabase();
                    resetSuccessful.setText("Successfully Reset Password");
                } catch (SQLException e) {
                    resetSuccessful.setText("Could not update password for given user");
                }
            } else {
                resetSuccessful.setText("Password fields do not match");
            }
        }
    }

    /**
     * Takes the user back to the login page
     * @param event
     */
    public void Back(ActionEvent event) {
        try {
            super.back(event);
        } catch (IOException e) {
            resetSuccessful.setText("Cannot Access Login Page");
        }
    }




}
