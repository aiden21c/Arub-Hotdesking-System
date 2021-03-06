package main.controller.editUserController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.controller.AbstractController;
import main.controller.singleton.UserSingleton;
import main.model.object.user.User;
import main.model.utilities.Utilities;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditUser extends AbstractController {
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

    private UserSingleton userSingleton;

    /**
     * Initializes the scene by instantiating the text fields with the current user attributes
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userSingleton = UserSingleton.getInstance();
        heading.setText("Edit " + userSingleton.getUser().getUsername().toUpperCase());

        employeeID.setText(Integer.toString(userSingleton.getUser().getEmpID()));
        firstName.setText(userSingleton.getUser().getFirstName());
        lastName.setText(userSingleton.getUser().getLastName());
        role.setText(userSingleton.getUser().getRole());
        username.setText(userSingleton.getUser().getUsername());
        age.setText(Integer.toString(userSingleton.getUser().getAge()));
        password.setText(userSingleton.getUser().getPassword());
        secretQ.setText(userSingleton.getUser().getSecretQuestion()[User.SecretQuestion.QUESTION.ordinal()]);
        secretA.setText(userSingleton.getUser().getSecretQuestion()[User.SecretQuestion.ANSWER.ordinal()]);
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

        if(success) {
            userSingleton.getUser().setFirstName(first);
            userSingleton.getUser().setLastName(last);
            userSingleton.getUser().setRole(r);
            userSingleton.getUser().setAge(a);
            userSingleton.getUser().setNewPassword(pw);
            userSingleton.getUser().setSecretQuestion(secretQuestion);

            try {
                userSingleton.writeToDatabase();
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

    /**
     * Takes the user back to their user settings page
     * @param event
     */
    public void back(ActionEvent event) {
        try {
            super.back(event);
        } catch (IOException e) {
            updateSuccess.setText("Cannot access UserSettings page");
        }
    }
}