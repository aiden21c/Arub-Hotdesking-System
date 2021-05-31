package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import main.Main;
import main.model.object.user.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
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

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = LoginController.user;
        newPasswordTitle.setText("Enter a New Password For " + user.getUsername());
    }

    public void Reset(ActionEvent event) {
        if(!txtPassword1.getText().isEmpty()) {
            if (txtPassword1.getText().equals(txtPassword2.getText())) {
                try {
                    user.setNewPassword(txtPassword1.getText());
                    Main.userDAO.addUser(user);
                    resetSuccessful.setText("Successfully Reset Password");
                } catch (SQLException e) {
                    resetSuccessful.setText("Could not update password for given user");
                }
            } else {
                resetSuccessful.setText("Password fields do not match");
            }
        }
    }

    public void Back(ActionEvent event) {
        try {
            newScene("login.fxml", event);
        } catch (IOException e) {
            //TODO correctly handle exception
            e.printStackTrace();
        }
    }




}
