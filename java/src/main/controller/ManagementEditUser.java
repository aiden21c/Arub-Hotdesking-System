package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

public class ManagementEditUser extends AbstractController {
    @FXML
    private Label heading;
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
    private Label updateSuccess;
    @FXML
    private Label ageError;
    @FXML
    private CheckBox adminCheck;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button whiteListButton;

    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = SearchUsernameController.editUser;
        heading.setText("Edit " + user.getUsername().toUpperCase());

        employeeID.setText(Integer.toString(user.getEmpID()));
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        role.setText(user.getRole());
        username.setText(user.getUsername());
        age.setText(Integer.toString(user.getAge()));
        password.setText(user.getPassword());
        secretQ.setText(user.getSecretQuestion()[User.SecretQuestion.QUESTION.ordinal()]);
        secretA.setText(user.getSecretQuestion()[User.SecretQuestion.ANSWER.ordinal()]);
        adminCheck.setSelected(user instanceof Admin);
    }

    /**
     * Obtains the data in the text fields and updates the user object accordingly
     * @param event
     */
    public void update(ActionEvent event) {
        ageError.setText("");
        updateSuccess.setText("");

        boolean success = true;
        String first, last, r, pw;
        first = last = r = pw = null;
        String un = user.getUsername();
        int empID = user.getEmpID();
        int a = 0;
        String[] secretQuestion = new String[2];

        if (!firstName.getText().isEmpty()) {
                first = firstName.getText();
        } else {success = false;}

        if (!lastName.getText().isEmpty()) {
            last = lastName.getText();
        } else {success = false;}

        if (!role.getText().isEmpty()) {
            r = role.getText();
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

        boolean admin = adminCheck.isSelected();

        if(success) {
            if(admin) {
                user = new Admin(empID, first, last, r, a, un, pw, secretQuestion, user.getWhiteList());
            } else {
                user = new Employee(empID, first, last, r, a, un, pw, secretQuestion, user.getWhiteList());
            }

            try {
                Main.userDAO.deleteUser(un);
                Main.userDAO.addUser(user);
                updateSuccess.setText("Update Successful");
            } catch (SQLException e) {
                updateFail();
            }
        } else {
            updateFail();
        }
    }

    /**
     * Sets the updateSuccess label to a fail message
     */
    private void updateFail() {
        updateSuccess.setText("Update Failed");
    }

    public void deleteUser(ActionEvent event) {
        try {
            String un = user.getUsername();
            Main.userDAO.deleteUser(un);
            heading.setText(un.toUpperCase() + " Was Deleted");
            updateSuccess.setText(un + " Was Deleted");
            hideFields(un);
            user = null;
        } catch (SQLException e) {
            updateSuccess.setText("User could not be deleted");
        }

    }

    public void editWhitelist(ActionEvent event) {
        updateSuccess.setText("Whitelist button pressed");
        //TODO
    }

    /**
     * Takes the user back to management page
     * @param event
     */
    public void back(ActionEvent event) {
        //TODO
        try {
            newScene("management.fxml", event);
        } catch (IOException e) {
            updateSuccess.setText("Cannot access management page");
        }
    }

    private void hideFields(String un) {
        firstName.setEditable(false);
        lastName.setEditable(false);
        role.setEditable(false);
        age.setEditable(false);
        password.setEditable(false);
        secretQ.setEditable(false);
        secretA.setEditable(false);
        adminCheck.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        whiteListButton.setDisable(true);
    }
}