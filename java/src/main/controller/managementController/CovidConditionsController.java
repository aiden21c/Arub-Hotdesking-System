package main.controller.managementController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.Main;
import main.controller.AbstractController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CovidConditionsController extends AbstractController {
    @FXML
    private Label updateLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    /**
     * Unlocks all seats
     * @param event
     */
    public void setNormalConditions(ActionEvent event) {
        try {
            Main.seatDAO.setNormalConditions();
            updateLabel.setText("Normal Conditions Set");
        } catch (SQLException e) {
            updateLabel.setText("Could Not Set Normal Conditions");
        }
    }

    /**
     * Sets the covid conditions by locking down every second seat
     *      All current/future bookings must be rebooked
     * @param event
     */
    public void setCovidConditions(ActionEvent event) {
        try {
            Main.seatDAO.setCovidConditions();
            Main.bookingDAO.setCovidConditions();
            updateLabel.setText("COVID Conditions Set");
        } catch (SQLException e) {
            updateLabel.setText("Could Not Set COVID Conditions");
        }
    }

    /**
     * Lockdowns all seats preventing all future booking
     *      All current/future bookings must be rebooked
     * @param event
     */
    public void setLockdownConditions(ActionEvent event) {
        try {
            Main.seatDAO.lockDown();
            Main.bookingDAO.setCovidConditions();
            updateLabel.setText("Lockdown Conditions Set");
        } catch (SQLException e) {
            updateLabel.setText("Could Not Set Lockdown Conditions");
        }
    }

    /**
     * Takes the user back to the management page
     * @param event
     */
    public void back(ActionEvent event) {
        try {
            super.back(event);
        } catch (IOException e) {
            updateLabel.setText("Cannot Access management Page");
        }
    }

}
