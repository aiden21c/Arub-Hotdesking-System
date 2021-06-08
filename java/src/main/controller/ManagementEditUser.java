package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.controller.singleton.EditUserSingleton;
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

    private EditUserSingleton editUserSingleton;

    /**
     * Initializes the edit user page, setting the text fields to the user's current information
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editUserSingleton = EditUserSingleton.getInstance();
        heading.setText("Edit " + editUserSingleton.getUser().getUsername().toUpperCase());

        employeeID.setText(Integer.toString(editUserSingleton.getUser().getEmpID()));
        firstName.setText(editUserSingleton.getUser().getFirstName());
        lastName.setText(editUserSingleton.getUser().getLastName());
        role.setText(editUserSingleton.getUser().getRole());
        username.setText(editUserSingleton.getUser().getUsername());
        age.setText(Integer.toString(editUserSingleton.getUser().getAge()));
        password.setText(editUserSingleton.getUser().getPassword());
        secretQ.setText(editUserSingleton.getUser().getSecretQuestion()[User.SecretQuestion.QUESTION.ordinal()]);
        secretA.setText(editUserSingleton.getUser().getSecretQuestion()[User.SecretQuestion.ANSWER.ordinal()]);
        adminCheck.setSelected(editUserSingleton.getUser() instanceof Admin);
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
        String un = editUserSingleton.getUser().getUsername();
        int empID = editUserSingleton.getUser().getEmpID();
        int a = 0;
        String[] secretQuestion = new String[2];

        if (!firstName.getText().isEmpty()) {first = firstName.getText();}
        else {success = false;}

        if (!lastName.getText().isEmpty()) {last = lastName.getText();}
        else {success = false;}

        if (!role.getText().isEmpty()) {r = role.getText();}
        else {success = false;}

        if (!age.getText().isEmpty()) {
            a = Utilities.isInt(age.getText());
            if (a == 0) {
                success = false;
                ageError.setText("Enter a Valid Number");
            }
        } else {success = false;}

        if (!password.getText().isEmpty()) {pw = password.getText();}
        else {success = false;}

        if (!secretQ.getText().isEmpty()) {
            secretQuestion[User.SecretQuestion.QUESTION.ordinal()] = secretQ.getText();
        } else {success = false;}

        if (!secretA.getText().isEmpty()) {
            secretQuestion[User.SecretQuestion.ANSWER.ordinal()] = secretA.getText();
        } else {success = false;}

        boolean admin = adminCheck.isSelected();

        if(success) {
            if(admin) {
                editUserSingleton.setUser(new Admin(empID, first, last, r, a, un, pw, secretQuestion, editUserSingleton.getUser().getWhiteList()));
            } else {
                editUserSingleton.setUser(new Employee(empID, first, last, r, a, un, pw, secretQuestion, editUserSingleton.getUser().getWhiteList()));
            }

            try {
                editUserSingleton.writeToDatabase();
                updateSuccess.setText("Update Successful");
            } catch (SQLException e) {updateFail();}
        } else {updateFail();}
    }

    /**
     * Sets the updateSuccess label to a fail message
     */
    private void updateFail() {
        updateSuccess.setText("Update Failed");
    }

    /**
     * Deletes the current user from the database
     * @param event
     */
    public void deleteUser(ActionEvent event) {
        try {
            String un = editUserSingleton.getUser().getUsername();
            editUserSingleton.deleteUser(false);
            heading.setText(un.toUpperCase() + " Was Deleted");
            updateSuccess.setText(un + " Was Deleted");
            hideFields();
            editUserSingleton.setUser(null);
        } catch (SQLException e) {
            updateSuccess.setText("User could not be deleted");
        }
    }

    /**
     * Takes the user to the edit whitelist page
     * @param event
     */
    public void editWhitelist(ActionEvent event) {
        try {
            newScene("editWhitelist.fxml", event);
        } catch (IOException e) {
            e.printStackTrace();
            updateSuccess.setText("EditWhiteList Unavailable");
        }
    }

    /**
     * Takes the user back to management page
     * @param event
     */
    public void back(ActionEvent event) {
        try {
            editUserSingleton.setUser(null);
            super.back(event);
        } catch (IOException e) {
            updateSuccess.setText("Cannot access management page");
        }
    }

    /**
     * Ensures the only available control element to be interacted with is the back button
     */
    private void hideFields() {
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