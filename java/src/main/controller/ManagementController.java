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

    public void confirmBookings(ActionEvent event) {
        try {
            newScene("confirmBookings.fxml", event);
        } catch (IOException e) {
            heading.setText("Cannot Access Confirm Bookings Page");
        }
    }

    public void covidConditions(ActionEvent event) {
        try {
            newScene("covidConditions.fxml", event);
        } catch (IOException e) {
            heading.setText("Cannot Access CovidConditions Page");
        }
    }

    public void addEmployee(ActionEvent event) {
        try {
            newScene("addEmployee.fxml", event);
        } catch (IOException e) {
            heading.setText("Cannot Access AddEmployee Page");
        }
    }

    public void editEmployee(ActionEvent event) {
        try {
            newScene("searchUsername.fxml", event);
        } catch (IOException e) {
            heading.setText("Cannot Access Search Page");
        }
    }

    public void generateReports(ActionEvent event) {
        try {
            newScene("generateReports.fxml", event);
        } catch (IOException e) {
            heading.setText("Cannot Access Reports Page");
        }
    }

    public void back(ActionEvent event) {
        try {
            super.back(event);
        } catch (IOException e) {
            heading.setText("Cannot Access Employee Page");
        }
    }






}
