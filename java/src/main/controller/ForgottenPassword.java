package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.controller.singleton.UserSingleton;
import main.model.object.user.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgottenPassword extends AbstractController {
    @FXML
    private Label resetTitle;
    @FXML
    private Label secretQuestion;
    @FXML
    private TextField txtAnswer;
    @FXML
    private Label incorrectAnswer;

    private UserSingleton userSingleton;
    private String question;
    private String answer;

    /**
     * Initialize the scene by setting the user to the captured user from the login page
     *      Sets the scene for the text within various labels
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        userSingleton = UserSingleton.getInstance();

        resetTitle.setText("Password Reset For\n" + userSingleton.getUser().getUsername());
        question = userSingleton.getUser().getSecretQuestion()[User.SecretQuestion.QUESTION.ordinal()];
        answer = userSingleton.getUser().getSecretQuestion()[User.SecretQuestion.ANSWER.ordinal()].toUpperCase();

        secretQuestion.setText(question);
    }

    /**
     * Checks if the given answer matches the answer for the given user
     *      User is taken to the passwordReset page if the answer is correct
     * @param event
     */
    public void Reset(ActionEvent event) {
        if(answer.equals(txtAnswer.getText().toUpperCase())) {

            try {
                newScene("passwordReset.fxml", event);
            } catch (IOException e) {
                incorrectAnswer.setText("Cannot Open Password Reset Page");
            }

        } else {
            incorrectAnswer.setText("Answer Incorrect");
        }
    }

    /**
     * Takes user back to the login page
     * @param event
     */
    public void Back(ActionEvent event) {
        //TODO
        try {
            newScene("login.fxml", event);
        } catch (IOException e) {
            // TODO correctly handle exception
            e.printStackTrace();
        }
    }

}
