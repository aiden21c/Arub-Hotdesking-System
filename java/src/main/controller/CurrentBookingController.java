package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.controller.singleton.BookingSingleton;
import main.controller.singleton.UserSingleton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CurrentBookingController extends AbstractController {
    @FXML
    private Label updateLabel;
    @FXML
    private Label bookingInfo;
    @FXML
    private Button cancel;
    @FXML
    private Button checkIn;


    private UserSingleton userSingleton;
    private BookingSingleton bookingSingleton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDate today = LocalDate.now();
        userSingleton = UserSingleton.getInstance();
        bookingSingleton = BookingSingleton.getInstance();
        try {
            bookingSingleton.searchBooking(userSingleton.getUser());
            LocalDate cutoff = bookingSingleton.getBooking().getDate().minusDays(2);

            if (!today.isEqual(bookingSingleton.getBooking().getDate())) {
                if (!today.isBefore(cutoff)) {
                    updateLabel.setText("Cannot alter Booking within 48 hours");
                    hideFields();
                } else {checkIn.setDisable(true);}
            } else {
                cancel.setDisable(true);
            }

            bookingInfo.setText(
                    "Seat No:\t" + bookingSingleton.getBooking().getSeat().getSeatNo() +
                    "\nDate:\t" + bookingSingleton.getBooking().getDate().toString()
            );

        } catch (SQLException | ClassNotFoundException e) {
            updateLabel.setText("No current booking for " + userSingleton.getUser().getUsername());
            hideFields();
        }
    }

    public void cancel(ActionEvent event) {
        try {
            bookingSingleton.deleteBooking();
            updateLabel.setText("Booking Was Deleted");
            hideFields();
        } catch (SQLException e) {
            updateLabel.setText("Booking could not be deleted");
            hideFields();
        }
    }

    public void checkIn(ActionEvent event) {
        try {
            bookingSingleton.getBooking().checkIn();
            userSingleton.writeToDatabase();
            bookingSingleton.writeToDatabase();
            updateLabel.setText("CheckIn Completed");
            hideFields();
        } catch (SQLException e) {
            updateLabel.setText("CheckIn Unavailable");
            hideFields();
        }
    }

    public void back(ActionEvent event) {
        try {
            bookingSingleton.setBooking(null);
            super.back(event);
        } catch (IOException e) {
            updateLabel.setText("Cannot Access Admin Page");
        }
    }

    private void hideFields() {
        cancel.setDisable(true);
        checkIn.setDisable(true);
    }

}
