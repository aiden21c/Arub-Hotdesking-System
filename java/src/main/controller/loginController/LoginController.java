package main.controller.loginController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.Main;
import main.controller.AbstractController;
import main.controller.singleton.UserSingleton;
import main.model.object.user.Admin;
import main.model.object.user.Employee;

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

    private UserSingleton userSingleton;

    /**
     * Initialise the login page and ensure all expired booking entries are removed from the database
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        userSingleton = UserSingleton.getInstance();
        userSingleton.setUser(null);

        try {
            Main.bookingDAO.deleteUnconfirmedBookings();
            Main.bookingDAO.deleteIncompleteBookings();
            isConnected.setText("Connected");
        } catch (SQLException e) {
            isConnected.setText("Not Connected");
        }
    }

    /**
     * Checks if the username and password match with a user within the database
     * @param event
     */
    public void Login(ActionEvent event){
        try {
            String sceneURL = "employee/";
            userSingleton.searchUser(txtUsername.getText());
            if (userSingleton.getUser().getPassword().equals(txtPassword.getText())) {
                if(userSingleton.getUser() instanceof Admin) {sceneURL = sceneURL.concat("adminPage.fxml");}
                else if(userSingleton.getUser() instanceof Employee) {sceneURL = sceneURL.concat("employeePage.fxml");}
                newScene(sceneURL, event);
            } else {
                isConnected.setText("Details Incorrect");
            }
        } catch (SQLException | ClassNotFoundException e) {
            isConnected.setText("Details Incorrect");
        } catch (IOException e) {
            isConnected.setText("Cannot Load New Page");
        }
    }

    /**
     * Takes the user to the registration page upon clicking of the register hyperlink
     * @param event
     */
    public void Register(ActionEvent event) {
        try {
            newScene("login/register.fxml", event);
        } catch (IOException e) {
            isConnected.setText("Registration page unavailable");
        }
    }

    /**
     * Takes the user to the ForgottenPassword page upon clicking of the Forgot Password hyperlink
     * @param event
     */
    public void ForgottenPassword(ActionEvent event) {
        try {
            userSingleton.searchUser(txtUsername.getText());
            newScene("login/forgottenPassword.fxml", event);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            isConnected.setText("Invalid Username");
        }
    }
}
