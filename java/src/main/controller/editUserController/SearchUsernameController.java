package main.controller.editUserController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.controller.AbstractController;
import main.controller.singleton.EditUserSingleton;
import main.controller.singleton.UserSingleton;

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

    /**
     * Initialises the user singleton and EditUser singleton instances
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userSingleton = UserSingleton.getInstance();
        editUserSingleton = EditUserSingleton.getInstance();
        editUserSingleton.setUser(null);
    }

    /**
     * Searches for a user in the database matching the username given
     *      Ensures the username given is not equal to the current logged in admin
     * @param event
     */
    public void search(ActionEvent event) {
        try {
            if(!username.getText().equalsIgnoreCase(userSingleton.getUser().getUsername())) {
                editUserSingleton.searchUser(username.getText());
                newScene("editUser/managementEditUser.fxml", event);
            } else {
                errorLabel.setText("Cannot Search For Yourself");
            }
        } catch (SQLException | ClassNotFoundException e) {
            errorLabel.setText("This username does not exist");
        } catch (IOException e) {
            errorLabel.setText("Could not load Edit page");
        }
    }

    /**
     * Takes the user back to the management page
     * @param event
     */
    public void back(ActionEvent event) {
        try {
            editUserSingleton.setUser(null);
            super.back(event);
        } catch (IOException e) {
            errorLabel.setText("Management Page Not Found");
        }
    }
}
