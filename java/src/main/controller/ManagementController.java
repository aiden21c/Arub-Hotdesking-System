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
        //TODO
    }

    public void covidConditions(ActionEvent event) {
        //TODO
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
        //TODO
    }

    public void back(ActionEvent event) {
        //TODO
        try {
            newScene("adminPage.fxml", event);
        } catch (IOException e) {
            heading.setText("Cannot Access Admin Page");
        }
    }






}
