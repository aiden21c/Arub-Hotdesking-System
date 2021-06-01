package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractController implements Initializable {

    /**
     * Creates a new scene from the given file name
     * @param sceneURL file name must be within the "src/main/ui" directory
     * @param event
     * @throws IOException if a file with the given filename cannot be found
     */
    public void newScene(String sceneURL, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ui/" + sceneURL));
        Parent root = (Parent) fxmlLoader.load();
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(root));
    }
}
