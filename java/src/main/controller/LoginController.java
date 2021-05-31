package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.Main;
import main.model.object.user.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends AbstractController {
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    static User user;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (Main.userDAO.isDbConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }

    }

    /**
     * Checks if the username and password match with a user within the database
     * @param event
     */
    public void Login(ActionEvent event){

        try {
            user = Main.userDAO.createUser(txtUsername.getText());

            if (user.getPassword().equals(txtPassword.getText())) {
                isConnected.setText("Login Successful");
//                try {
//                    newScene("<sceneURL>");
//                } catch () { }


            } else {
                isConnected.setText("Details Incorrect");
            }
        } catch (SQLException | ClassNotFoundException e) {
            isConnected.setText("Details Incorrect");
        }
    }

    /**
     * Takes the user to the registration page upon clicking of the register hyperlink
     * @param event
     */
    public void Register(ActionEvent event) {
        try {
            newScene("register.fxml", event);
        } catch (IOException e) {
            // TODO correctly handle exception
            e.printStackTrace();
        }

    }

    /**
     * Takes the user to the ForgottenPassword page upon clicking of the Forgot Password hyperlink
     * @param event
     */
    public void ForgottenPassword(ActionEvent event) {
        try {
            user = Main.userDAO.createUser(txtUsername.getText());
            newScene("forgottenPassword.fxml", event);

        } catch (SQLException | ClassNotFoundException | IOException e) {
            isConnected.setText("Username Incorrect");
        }
    }
}
