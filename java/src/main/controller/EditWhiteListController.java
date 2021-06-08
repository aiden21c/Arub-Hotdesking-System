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
import main.Main;
import main.controller.singleton.EditUserSingleton;
import main.model.object.seat.Seat;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class EditWhiteListController extends AbstractController {
    @FXML
    private Label heading;
    @FXML
    private Label updateSuccess;
    @FXML
    private Button save;

    @FXML
    private ArrayList<Rectangle> rectangles;

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

    private EditUserSingleton editUserSingleton;
    private ArrayList<Seat> allSeats;
    private ArrayList<Seat> newWhiteList;

    /**
     * Initializes the scene
     *      Initializes the allSeats array and the the rectangles array
     *      Sets the context menu and the colours for the rectangles based on their seat status
     * @param location
     * @param resources
     */
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

        for (Rectangle rectangle : rectangles) {
            setContextMenu(rectangle);
            rectangle.setFill(Color.RED);
        }

        setColours();
    }

    /**
     * Takes the user back to the edit user page
     * @param event
     */
    public void back(ActionEvent event) {
        try {
            super.back(event);
        } catch (IOException e) {
            updateSuccess.setText("EditUser Page Unavailable");
        }
    }

    /**
     * Saves the current user with their updated whitelist to the database
     * @param event
     */
    public void save(ActionEvent event) {
        try {
            editUserSingleton.getUser().setWhitelist(newWhiteList);
            editUserSingleton.writeToDatabase();
            updateSuccess.setText("WhiteList Updated");
        } catch (SQLException e) {
            updateSuccess.setText("Could not save WhiteList");
        }
    }

    /**
     * Sets the context menu for the given rectangle
     * @param seatX the rectangle the context menu is to be set on
     * @param cm the context menu to be associated with the rectangle
     */
    private void setContextMenu(Rectangle seatX, ContextMenu cm) {
        seatX.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton().toString().equals("SECONDARY"))
                    cm.show(seatX, e.getScreenX(), e.getScreenY());
            }
        });
    }

    /**
     * Instantiates a new context menu to be associated with a rectangle
     * @param seatX the rectangle the context menu is being set on
     */
    private void setContextMenu(Rectangle seatX) {
        ContextMenu cm = new ContextMenu();
        MenuItem mi1 = new MenuItem("Lock");
        MenuItem mi2 = new MenuItem("Unlock");

        mi1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateWhiteList(seatX, true);
                event.consume();
            }
        });

        mi2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateWhiteList(seatX, false);
                event.consume();
            }
        });
        cm.getItems().addAll(mi1, mi2);
        setContextMenu(seatX, cm);
    }

    /**
     * Adds all the seat rectangles to the rectangles array
     */
    private void instantiateRectangleList() {
        rectangles = new ArrayList<Rectangle>();
        Collections.addAll(
                rectangles, seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8,
                seat9, seat10, seat11, seat12, seat13, seat14, seat15, seat16
        );
    }

    /**
     * Sets the colour of all rectangles that represent seats that are currently in the whitelist to green
     */
    private void setColours() {
        for (int i = 0; i < editUserSingleton.getUser().getWhiteList().size(); i++) {
            int seatNo = editUserSingleton.getUser().getWhiteList().get(i).getSeatNo();
            newWhiteList.add(allSeats.get(seatNo - 1));
            rectangles.get(seatNo - 1).setFill(Color.GREEN);
        }
    }

    /**
     * Updates the whitelist upon interaction, either removing the seat from or adding it to the whitelist
     * @param seatXrect the rectangle representing the seat
     * @param lock whether the seat is being added or removed from the whitelist
     */
    private void updateWhiteList(Rectangle seatXrect, boolean lock) {
        int index = rectangles.indexOf(seatXrect);
        Seat seatX = allSeats.get(index);

        if(newWhiteList.contains(seatX)) {
            if (lock) {
                newWhiteList.remove(seatX);
                rectangles.get(seatX.getSeatNo() - 1).setFill(Color.RED);
                updateSuccess.setText(seatXrect.getId().toUpperCase() + " Locked");
            }
        } else {
            if (!lock) {
                newWhiteList.add(seatX);
                rectangles.get(seatX.getSeatNo() - 1).setFill(Color.GREEN);
                updateSuccess.setText(seatXrect.getId().toUpperCase() + " Unlocked");
            }
        }
    }
}