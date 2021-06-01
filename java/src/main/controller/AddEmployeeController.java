package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import main.model.object.user.Admin;
import main.model.object.user.Employee;
import main.model.object.user.User;
import main.model.utilities.Utilities;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddEmployeeController extends AbstractController {
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
    @FXML
    private CheckBox adminCheck;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void addUser(ActionEvent event) {
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
            boolean admin = adminCheck.isSelected();
            User user;
            try {
                if(admin) {
                    user = new Admin(empID, first, last, r, a, un, pw, secretQuestion);
                } else {
                    user = new Employee(empID, first, last, r, a, un, pw, secretQuestion);
                }
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
        //TODO
        try {
            newScene("management.fxml", event);
        } catch (IOException e) {
            registrationSuccess.setText("Could not access Management page");
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
