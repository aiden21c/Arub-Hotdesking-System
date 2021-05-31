package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
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

    /* login Action method
       check if user input is the same as database.
     */
    public void Login(ActionEvent event){

        try {
            user = Main.userDAO.createUser(txtUsername.getText());

            if (user.getPassword().equals(txtPassword.getText())) {
                isConnected.setText("Login Successful");

//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout.fxml"));
//                Parent root = (Parent) fxmlLoader.load();
//                Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
//
//                primaryStage.setScene(new Scene(root));


            } else {
                isConnected.setText("Details Incorrect");
            }
        } catch (SQLException | ClassNotFoundException e) {
            isConnected.setText("Details Incorrect");
        }
    }

    public void Register(ActionEvent event) {
        System.out.println("Register button pressed");
    }

    public void ForgottenPassword(ActionEvent event) {
        try {
            user = Main.userDAO.createUser(txtUsername.getText());

            newScene("forgottenPassword.fxml", event);

        } catch (SQLException | ClassNotFoundException | IOException e) {
            isConnected.setText("Username Incorrect");
        }

    }
}
