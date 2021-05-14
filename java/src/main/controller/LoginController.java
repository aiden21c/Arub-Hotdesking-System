package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.Main;
import main.model.object.user.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (Main.userDAO.isDbConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }

    }
    /* login Action method
       check if user input is the same as database.
     */
    public void Login(ActionEvent event){

        try {
            User user = Main.userDAO.createUser(txtUsername.getText());

            if (user.getPassword().equals(txtPassword.getText())) {

                isConnected.setText("Logged in successfully");
            } else {
                isConnected.setText("username and password is incorrect");
            }
        } catch (SQLException | ClassNotFoundException e) {
            isConnected.setText("username and password is incorrect");
        }
    }
}
