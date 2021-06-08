package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagementController extends AbstractController {
    @FXML
    private Label heading;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    /**
     * Takes the user to the confirm bookings page
     * @param event
     */
    public void confirmBookings(ActionEvent event) {
        try {
            newScene("confirmBookings.fxml", event);
        } catch (IOException e) {
            heading.setText("Cannot Access Confirm Bookings Page");
        }
    }

    /**
     * Takes the user to the covid conditions page
     * @param event
     */
    public void covidConditions(ActionEvent event) {
        try {
            newScene("covidConditions.fxml", event);
        } catch (IOException e) {
            heading.setText("Cannot Access CovidConditions Page");
        }
    }

    /**
     * Takes the user to the add employee page
     * @param event
     */
    public void addEmployee(ActionEvent event) {
        try {
            newScene("addEmployee.fxml", event);
        } catch (IOException e) {
            heading.setText("Cannot Access AddEmployee Page");
        }
    }

    /**
     * Takes the user to the edit employee page
     * @param event
     */
    public void editEmployee(ActionEvent event) {
        try {
            newScene("searchUsername.fxml", event);
        } catch (IOException e) {
            heading.setText("Cannot Access Search Page");
        }
    }

    /**
     * Takes the user to the generate reports page
     * @param event
     */
    public void generateReports(ActionEvent event) {
        try {
            newScene("generateReports.fxml", event);
        } catch (IOException e) {
            heading.setText("Cannot Access Reports Page");
        }
    }

    /**
     * Takes the user back to the standard employee page
     * @param event
     */
    public void back(ActionEvent event) {
        try {
            super.back(event);
        } catch (IOException e) {
            heading.setText("Cannot Access Employee Page");
        }
    }






}
