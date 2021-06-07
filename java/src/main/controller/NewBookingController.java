package main.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.event.ActionEvent;
import main.Main;
import main.controller.singleton.BookingSingleton;
import main.controller.singleton.UserSingleton;
import main.model.object.booking.Booking;
import main.model.object.seat.Seat;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class NewBookingController extends AbstractController {
    @FXML
    private Label seatNo;

    @FXML
    private Button bookButton;
    @FXML
    private Button goButton;

    @FXML
    private DatePicker datePicker;

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

    private LocalDate date;

    private ArrayList<Seat> allSeats;

    private BookingSingleton bookingSingleton;
    private UserSingleton userSingleton;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookingSingleton = BookingSingleton.getInstance();
        userSingleton = UserSingleton.getInstance();
        boolean hasCurrentBooking = true;

        try {
            bookingSingleton.searchBooking(userSingleton.getUser());
        } catch (SQLException | ClassNotFoundException e) {
            hasCurrentBooking = false;
        }

        if(!hasCurrentBooking) {
            instantiateRectangleList();
            try {
                allSeats = Main.seatDAO.getAllSeats();
            } catch (SQLException e) {
                seatNo.setText("Could Not Load Seats");
            }
        } else {
            seatNo.setText("Current Booking Found");
            goButton.setDisable(true);
        }
    }

    public void go(ActionEvent event) {
        seatNo.setText("");
        LocalDate now = LocalDate.now();
        LocalDate earliestAvail = now.plusDays(2);
        if(datePicker.getValue() != null) {
            date = datePicker.getValue();
            if (date.isBefore(earliestAvail)) {
                seatNo.setText("Date Invalid");
            } else {
                prepareSeats();
            }
        }
    }

    public void book(ActionEvent event) {
        try {
            bookingSingleton.writeToDatabase();
            goButton.setDisable(true);
            bookButton.setDisable(true);
            seatNo.setText("Seat " + bookingSingleton.getBooking().getSeat().getSeatNo() + " Booked on " + date.getMonth() + " " + date.getDayOfMonth());

        } catch (SQLException e) {
            seatNo.setText("Booking Unsuccessful");
        }
    }


    public void back(ActionEvent event) {
        try {
            bookingSingleton.setBooking(null);
            super.back(event);
        } catch (IOException e) {
            seatNo.setText("Employee Page Unavailable");
        }
    }

    private void instantiateRectangleList() {
        rectangles = new ArrayList<Rectangle>();
        Collections.addAll(
                rectangles, seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8,
                seat9, seat10, seat11, seat12, seat13, seat14, seat15, seat16
        );
    }

    private void prepareSeats() {
        for(int i = 0; i < allSeats.size(); i++) {
            if(allSeats.get(i).isLockedOnDate(date).equals(Seat.Available.LOCKED)) {
                rectangles.get(i).setFill(Color.RED);

            } else if(allSeats.get(i).isLockedOnDate(date).equals(Seat.Available.BOOKED)) {
                rectangles.get(i).setFill(Color.ORANGE);

            } else if(allSeats.get(i).isLockedOnDate(date).equals(Seat.Available.OPEN)) {
                rectangles.get(i).setFill(Color.GREEN);
                setContextMenu(rectangles.get(i));
            }
        }
    }

    private void setContextMenu(Rectangle seatX, ContextMenu cm) {
        seatX.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (!seatX.getFill().equals(Color.RED) && !seatX.getFill().equals(Color.ORANGE)) {
                    if (e.getButton().toString().equals("SECONDARY")) {
                        cm.show(seatX, e.getScreenX(), e.getScreenY());
                    }
                }
            }
        });
    }

    private void setContextMenu(Rectangle seatX) {
        ContextMenu cm = new ContextMenu();
        MenuItem mi1 = new MenuItem("Book");

        mi1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                book(seatX);
                event.consume();
            }
        });

        cm.getItems().addAll(mi1);
        setContextMenu(seatX, cm);
    }

    private void book(Rectangle seatXrect) {
        bookingSingleton.setBooking(null);
        int seatIndex = rectangles.indexOf(seatXrect);
        Booking booking = new Booking(allSeats.get(seatIndex), userSingleton.getUser(), date);
        bookingSingleton.setBooking(booking);

        seatNo.setText("Booking for Seat " + bookingSingleton.getBooking().getSeat().getSeatNo());
        bookButton.setDisable(false);
    }
}
