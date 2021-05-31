package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private User user;
    private String question;
    private String answer;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        user = LoginController.user;
        resetTitle.setText("Password Reset For " + user.getUsername());
        question = user.getSecretQuestion()[User.SecretQuestion.QUESTION.ordinal()];
        answer = user.getSecretQuestion()[User.SecretQuestion.ANSWER.ordinal()].toUpperCase();

        secretQuestion.setText(question);
    }

    public void Reset(ActionEvent event) {
        if(answer.equals(txtAnswer.getText().toUpperCase())) {

            try {
                newScene("passwordReset.fxml", event);
            } catch (IOException e) {
                // TODO Handle exception
                e.printStackTrace();
            }

        } else {
            incorrectAnswer.setText("Answer Incorrect");
        }
    }

    public void Back(ActionEvent event) { }

}
