package main.controller.bookingController;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.event.ActionEvent;
import main.Main;
import main.controller.AbstractController;
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
    private Button cancelButton;

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

    /**
     * Initializes the scene
     * Ensures the user cannot make a second booking if they already have a current booking
     *
     * @param location
     * @param resources
     */
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

        if (!hasCurrentBooking) {
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

    /**
     * Ensures the date selected is 2 or more days in the future
     * Displays the current booking outlay of seats on the given date
     *
     * @param event
     */
    public void go(ActionEvent event) {
        seatNo.setText("");
        LocalDate now = LocalDate.now();
        LocalDate earliestAvail = now.plusDays(2);
        if (datePicker.getValue() != null) {
            date = datePicker.getValue();
            if (date.isBefore(earliestAvail)) {
                seatNo.setText("Date Invalid");
            } else {
                prepareSeats();
            }
        }
    }

    /**
     * Confirms the booking of the seat selected for the user on the given date and writes to database
     *
     * @param event
     */
    public void book(ActionEvent event) {
        try {
            bookingSingleton.writeToDatabase();
            goButton.setDisable(true);
            bookButton.setDisable(true);
            cancelButton.setDisable(true);
            seatNo.setText("Seat " + bookingSingleton.getBooking().getSeat().getSeatNo() + " Booked on " + date.getMonth() + " " + date.getDayOfMonth());
            rectangles.get(bookingSingleton.getBooking().getSeat().getSeatNo() - 1).setFill(Color.ORANGE);
        } catch (SQLException e) {
            seatNo.setText("Booking Unsuccessful");
        }
    }

    /**
     * Takes the user back to the employee page
     *
     * @param event
     */
    public void back(ActionEvent event) {
        try {
            bookingSingleton.setBooking(null);
            super.back(event);
        } catch (IOException e) {
            seatNo.setText("Employee Page Unavailable");
        }
    }

    /**
     * Adds all the seat rectangles to the rectangle array
     */
    private void instantiateRectangleList() {
        rectangles = new ArrayList<Rectangle>();
        Collections.addAll(
                rectangles, seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8,
                seat9, seat10, seat11, seat12, seat13, seat14, seat15, seat16
        );
    }

    /**
     * Prepares the seat rectangle colours, sets the context menu for only green rectangles
     */
    private void prepareSeats() {
        for (int i = 0; i < allSeats.size(); i++) {
            if (allSeats.get(i).isLockedOnDate(date).equals(Seat.Available.LOCKED)) {
                rectangles.get(i).setFill(Color.RED);

            } else if (allSeats.get(i).isLockedOnDate(date).equals(Seat.Available.BOOKED)) {
                rectangles.get(i).setFill(Color.ORANGE);

            } else if (allSeats.get(i).isLockedOnDate(date).equals(Seat.Available.OPEN)) {
                rectangles.get(i).setFill(Color.GREEN);
            }
            setContextMenu(rectangles.get(i));
        }
    }

    /**
     * Sets the context menu given for the given rectangle seat
     *
     * @param seatX the seat the context menu is being set for
     * @param cm    the context menu being set
     */
    private void setContextMenu(Rectangle seatX, ContextMenu cm) {
        seatX.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (seatX.getFill().equals(Color.GREEN)) {
                    if(bookingSingleton.getBooking() == null) {
                        if (e.getButton().toString().equals("SECONDARY")) {
                            cm.show(seatX, e.getScreenX(), e.getScreenY());
                        }
                    }
                }
            }
        });
    }

    /**
     * Instantiates a new context menu associated with the given seat
     *
     * @param seatX the seat the context menu is being set for
     */
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

    /**
     * Places the seat selected in 'booking pending' mode, waiting for the user to confirm the booking
     * @param seatXrect the rectangle associated with the seat being booked
     */
    private void book(Rectangle seatXrect) {
        bookingSingleton.setBooking(null);
        int seatIndex = rectangles.indexOf(seatXrect);
        Booking booking = new Booking(allSeats.get(seatIndex), userSingleton.getUser(), date);
        bookingSingleton.setBooking(booking);

        seatNo.setText("Booking for Seat " + bookingSingleton.getBooking().getSeat().getSeatNo());
        seatXrect.setFill(Color.ORANGE);
        goButton.setDisable(true);
        bookButton.setDisable(false);
        cancelButton.setDisable(false);
    }

    /**
     * Cancels the booking in pending state
     * @param event
     */
    public void cancel(ActionEvent event) {
        int rectanglesIndex = allSeats.indexOf(bookingSingleton.getBooking().getSeat());
        rectangles.get(rectanglesIndex).setFill(Color.GREEN);
        goButton.setDisable(false);
        bookButton.setDisable(true);
        cancelButton.setDisable(true);
        seatNo.setText("");
        bookingSingleton.setBooking(null);
    }
}
