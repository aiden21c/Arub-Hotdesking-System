package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.model.dao.seat.BlockOutDAO;
import main.model.dao.seat.SeatDAO;
import main.model.dao.user.SecretQuestionDAO;
import main.model.dao.user.UserDAO;
import main.model.dao.user.WhiteListDAO;

public class Main extends Application {
    public static final UserDAO userDAO = new UserDAO();
    public static final SeatDAO seatDAO = new SeatDAO();
    public static final SecretQuestionDAO secretQuestionDAO = new SecretQuestionDAO();
    public static final WhiteListDAO whiteListDAO = new WhiteListDAO();
    public static final BlockOutDAO blockOutDAO = new BlockOutDAO();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ui/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
