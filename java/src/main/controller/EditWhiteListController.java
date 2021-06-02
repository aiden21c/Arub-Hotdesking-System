package main.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;
import main.Main;
import main.controller.singleton.EditUserSingleton;
import main.model.object.seat.Seat;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditWhiteListController extends AbstractController {
    @FXML
    private Label heading;
    @FXML
    private Label updateSuccess;
    @FXML
    private Button save;

    @FXML
    private Rectangle[] rectangles;

    @FXML
    private Rectangle seat1;
    @FXML
    private Rectangle seat2;
    @FXML
    private Rectangle seat3;
    @FXML
    private Rectangle seat4;
    @FXML
    private Rectangle seat5;
    @FXML
    private Rectangle seat6;
    @FXML
    private Rectangle seat7;
    @FXML
    private Rectangle seat8;
    @FXML
    private Rectangle seat9;
    @FXML
    private Rectangle seat10;
    @FXML
    private Rectangle seat11;
    @FXML
    private Rectangle seat12;
    @FXML
    private Rectangle seat13;
    @FXML
    private Rectangle seat14;
    @FXML
    private Rectangle seat15;
    @FXML
    private Rectangle seat16;

    @FXML
    ContextMenu cM1;


    private EditUserSingleton editUserSingleton;
    private ArrayList<Seat> allSeats;
    private ArrayList<Seat> newWhiteList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editUserSingleton = EditUserSingleton.getInstance();
        instantiateRectangleList();
        newWhiteList = new ArrayList<>();
        heading.setText(editUserSingleton.getUser().getUsername().toUpperCase());
        try {
            allSeats = Main.seatDAO.getAllSeats();
        } catch (SQLException e) {
            save.setDisable(true);
            updateSuccess.setText("Could Not Load Seats");
        }

        setContextMenu();
        for (int i = 0; i < rectangles.length; i++) {
            setContextMenu(rectangles[i]);
            rectangles[i].setFill(Color.RED);
        }

        setColours();

    }

    public void back(ActionEvent event) {
        try {
            newScene("managementEditUser.fxml", event);
        } catch (IOException e) {
            updateSuccess.setText("EditUser Page Unavailable");
        }
    }

    public void save(ActionEvent event) {

    }

    private void setContextMenu(Rectangle seatX) {
        seatX.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton().toString().equals("SECONDARY"))
                    cM1.show(seatX, e.getScreenX(), e.getScreenY());
            }
        });
    }

    private void setContextMenu() {
        cM1 = new ContextMenu();
        MenuItem mi1 = new MenuItem("Lock");
        MenuItem mi2 = new MenuItem("Unlock");

        mi1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateSuccess.setText("Lock button pressed");
                event.consume();
            }
        });

        mi2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateSuccess.setText("Unlock button pressed");
                event.consume();
            }
        });
        cM1.getItems().addAll(mi1, mi2);
    }

    private void instantiateRectangleList() {
        rectangles = new Rectangle[]{
                seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8,
                seat9, seat10, seat11, seat12, seat13, seat14, seat15, seat16
        };
    }

    private void setColours() {
        for (int i = 0; i < editUserSingleton.getUser().getWhiteList().size(); i++) {
            int seatNo = editUserSingleton.getUser().getWhiteList().get(i).getSeatNo();
            newWhiteList.add(editUserSingleton.getUser().getWhiteList().get(i));
            rectangles[seatNo - 1].setFill(Color.GREEN);
        }
    }




    /*
    private class CustomContextMenu extends ContextMenu {
        public CustomContextMenu(Rectangle rectangle) {
            MenuItem mi1 = new MenuItem("Lock");
            MenuItem mi2 = new MenuItem("Unlock");
            setMenuItemEvent(mi1, rectangle);
            setMenuItemEvent(mi2, rectangle);

            getItems().addAll(mi1, mi2);
        }

        private void setMenuItemEvent(MenuItem m, Rectangle r) {
            m.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    updateSuccess.setText(r.toString() + "lock");
                    event.consume();
                }
            });
        }
    }
     */
}