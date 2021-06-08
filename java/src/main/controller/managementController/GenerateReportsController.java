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

public class GenerateReportsController extends AbstractController {
    @FXML
    private Label updateLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    /**
     * Creates a CSV file containing all the bookings in the database
     * @param event
     */
    public void generateBookingReport(ActionEvent event) {
        try {
            Main.bookingDAO.export();
            updateLabel.setText("Booking Report Generated");
        } catch (SQLException | IOException e) {
            updateLabel.setText("Booking Report Could Not Be Generated");
        }
    }

    /**
     * Creates two CSV files
     *      One containing all the employee data
     *      One containing all whitelist information for all employees
     * @param event
     */
    public void generateEmployeeReport(ActionEvent event) {
        try {
            Main.userDAO.export();
            Main.whiteListDAO.export();
            updateLabel.setText("Employee Reports Generated");
        } catch (SQLException | IOException e) {
            updateLabel.setText("Employee Reports Could Not Be Generated");
        }
    }

    /**
     * Takes the user back to the management page
     * @param event
     */
    public void done(ActionEvent event) {
        try {
            super.back(event);
        } catch (IOException e) {
            updateLabel.setText("Cannot Access Management Page");
        }
    }

}
