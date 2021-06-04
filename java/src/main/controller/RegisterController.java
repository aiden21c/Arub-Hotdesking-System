package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import main.model.object.user.Employee;
import main.model.object.user.User;
import main.model.utilities.Utilities;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController extends AbstractController {
    @FXML
    private TextField employeeID;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField role;
    @FXML
    private TextField username;
    @FXML
    private TextField age;
    @FXML
    private TextField password;
    @FXML
    private TextField secretQ;
    @FXML
    private TextField secretA;
    @FXML
    private Label employeeIDerror;
    @FXML
    private Label usernameError;
    @FXML
    private Label registrationSuccess;
    @FXML
    private Label ageError;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    /**
     * Captures information from the registration form and creates a new Employee
     *      If the user is valid, it is written to the database
     * @param event
     */
    public void Register(ActionEvent event) {
        clearLabels();
        boolean success = true;
        String first, last, r, un, pw;
        first = last = r = un = pw = null;
        int empID = 0; int a = 0;
        String[] secretQuestion = new String[2];

        if (!employeeID.getText().isEmpty()) {
            empID = Utilities.isInt(employeeID.getText());
            if (empID == 0) {
                success = false;
                employeeIDerror.setText("Enter a Valid Number");
            }
        } else {success = false;}

        if (!firstName.getText().isEmpty()) {
            first = firstName.getText();
        } else {success = false;}

        if (!lastName.getText().isEmpty()) {
            last = lastName.getText();
        } else {success = false;}

        if (!role.getText().isEmpty()) {
            r = role.getText();
        } else {success = false;}

        if (!username.getText().isEmpty()) {
            un = username.getText();
        } else {success = false;}

        if (!age.getText().isEmpty()) {
            a = Utilities.isInt(age.getText());
            if (a == 0) {
                success = false;
                ageError.setText("Enter a Valid Number");
            }
        } else {success = false;}

        if (!password.getText().isEmpty()) {
            pw = password.getText();
        } else {success = false;}

        if (!secretQ.getText().isEmpty()) {
            secretQuestion[User.SecretQuestion.QUESTION.ordinal()] = secretQ.getText();
        } else {success = false;}

        if (!secretA.getText().isEmpty()) {
            secretQuestion[User.SecretQuestion.ANSWER.ordinal()] = secretA.getText();
        } else {success = false;}

        if(success) {
            try {
                User user = new Employee(empID, first, last, r, a, un, pw, secretQuestion);
                if(!Main.userDAO.exists(un)) {
                    Main.userDAO.addUser(user);
                    registrationSuccess.setText("Registration Successful");
                } else {
                    usernameError.setText("Username Already Exists");
                    regFail();
                }
            } catch (SQLException e) {
                regFail();
            }
        } else {
            regFail();
        }
    }

    /**
     * Takes the user back to the login page
     * @param event
     */
    public void back(ActionEvent event) {
        try {
            super.back(event);
        } catch (IOException e) {
            registrationSuccess.setText("Could not access Login page");
        }
    }

    /**
     * Sets the registrationSuccess label to a fail message
     */
    private void regFail() {
        registrationSuccess.setText("Registration Failed");
    }

    /**
     * Clears all erroneous label messages and makes them blank
     */
    private void clearLabels() {
        usernameError.setText("");
        employeeIDerror.setText("");
        ageError.setText("");
        registrationSuccess.setText("");
    }
}
