package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import main.controller.singleton.EditUserSingleton;
import main.controller.singleton.UserSingleton;
import main.model.object.user.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchUsernameController extends AbstractController {
    @FXML
    private Label errorLabel;
    @FXML
    private TextField username;

    private EditUserSingleton editUserSingleton;
    private UserSingleton userSingleton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userSingleton = UserSingleton.getInstance();
        editUserSingleton = EditUserSingleton.getInstance();
        editUserSingleton.setUser(null);
    }

    public void search(ActionEvent event) {
        try {
            if(!username.getText().equalsIgnoreCase(userSingleton.getUser().getUsername())) {
                editUserSingleton.setUser(Main.userDAO.createUser(username.getText()));
                newScene("managementEditUser.fxml", event);
            } else {
                errorLabel.setText("Cannot Search For Yourself");
            }
        } catch (SQLException | ClassNotFoundException e) {
            errorLabel.setText("This username does not exist");
        } catch (IOException e) {
            errorLabel.setText("Could not load Edit page");
        }
    }

    public void back(ActionEvent event) {
        //TODO
        try {
            editUserSingleton.setUser(null);
            newScene("management.fxml", event);
        } catch (IOException e) {
            errorLabel.setText("Management Page Not Found");
        }
    }
}
