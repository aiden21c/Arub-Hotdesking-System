package main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Main;
import main.model.object.booking.Booking;
import main.model.object.seat.Seat;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class ConfirmBookingController extends AbstractController {
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label updateSuccess;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instantiateRectangleList();
        try {
            allSeats = Main.seatDAO.getAllSeats();
        } catch (SQLException e) {
            updateSuccess.setText("Could Not Load Seats");
        }
    }

    private void instantiateRectangleList() {
        rectangles = new ArrayList<Rectangle>();
        Collections.addAll(
                rectangles, seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8,
                seat9, seat10, seat11, seat12, seat13, seat14, seat15, seat16
        );
    }

    public void go(ActionEvent event) {
        updateSuccess.setText("");
        LocalDate now = LocalDate.now();
        if (datePicker.getValue() != null) {
            date = datePicker.getValue();
            if (date.isBefore(now)) {
                updateSuccess.setText("Date Invalid");
            } else {
                try {
                    prepareSeats();
                    if (date.isEqual(now)) {
                        updateSuccess.setText("Cannot alter today");
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    updateSuccess.setText("Seats Unavailable");
                }
            }
        }
    }

    private void prepareSeats() throws SQLException, ClassNotFoundException {
        for (int i = 0; i < allSeats.size(); i++) {
            if (allSeats.get(i).isLockedOnDate(date).equals(Seat.Available.LOCKED)) {
                rectangles.get(i).setFill(Color.RED);

            } else if (allSeats.get(i).isLockedOnDate(date).equals(Seat.Available.BOOKED)) {
                Booking booking = Main.bookingDAO.createCurrentBooking(allSeats.get(i).getSeatNo(), date);
                if (booking.getPending()) {
                    rectangles.get(i).setFill(Color.BLUE);
                } else {
                    rectangles.get(i).setFill(Color.ORANGE);
                }

            } else if (allSeats.get(i).isLockedOnDate(date).equals(Seat.Available.OPEN)) {
                rectangles.get(i).setFill(Color.GREEN);
            }
            setContextMenu(rectangles.get(i));
        }
    }

    private void setContextMenu(Rectangle seatX, ContextMenu cm) {
        seatX.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (!date.isEqual(LocalDate.now())) {
                    if (e.getButton().toString().equals("SECONDARY")) {
                        try {
                            if (seatX.getFill().equals(Color.BLUE) || seatX.getFill().equals(Color.ORANGE)) {
                                Seat seat = allSeats.get(getIndex(seatX));
                                Booking booking = Main.bookingDAO.createCurrentBooking(seat.getSeatNo(), date);
                                updateSuccess.setText("Booked for: " + booking.getUser().getUsername());
                            } else {
                                updateSuccess.setText("");
                            }

                            cm.show(seatX, e.getScreenX(), e.getScreenY());

                        } catch (SQLException | ClassNotFoundException throwables) {
                            updateSuccess.setText(seatX.getId().toUpperCase() + " Invalid");
                        }
                    }
                }

            }
        });
    }

    private void setContextMenu(Rectangle seatX) {
        ContextMenu cm = new ContextMenu();
        MenuItem mi1 = new MenuItem("Release");
        MenuItem mi2 = new MenuItem("Lock");
        MenuItem mi3 = new MenuItem("Accept");

        mi1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    release(seatX);
                } catch (SQLException e) {
                    updateSuccess.setText(seatX.getId().toUpperCase() + " Unavailable");
                }
                event.consume();
            }
        });

        mi2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    lock(seatX);
                } catch (SQLException e) {
                    updateSuccess.setText("Could not Lock seat");
                }
                event.consume();
            }
        });

        mi3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    accept(seatX);
                } catch (SQLException | ClassNotFoundException e) {
                    updateSuccess.setText("No Booking For " + seatX.getId().toUpperCase());
                }
                event.consume();
            }
        });


        cm.getItems().addAll(mi1, mi2, mi3);
        setContextMenu(seatX, cm);
    }

    public void back(ActionEvent event) {
        try {
            super.back(event);
        } catch (IOException e) {
            updateSuccess.setText("Cannot Access Management Page");
        }
    }

    private int getIndex(Rectangle seatX) {
        return rectangles.indexOf(seatX);
    }

    private void lock(Rectangle seatX) throws SQLException {
        Seat seat = allSeats.get(getIndex(seatX));
        if (!seat.getBlockOut()) {
            if (seatX.getFill().equals(Color.BLUE) || seatX.getFill().equals(Color.ORANGE)) {
                Main.bookingDAO.deleteIncompleteBooking(seat.getSeatNo(), date);
            }
            seat.lockDate(date);
            Main.seatDAO.addSeat(seat);
            seatX.setFill(Color.RED);
            updateSuccess.setText("Seat " + seat.getSeatNo() + " Locked");
        } else {
            updateSuccess.setText("Seat COVID Locked");
        }
    }

    private void accept(Rectangle seatX) throws SQLException, ClassNotFoundException {
        Seat seat = allSeats.get(getIndex(seatX));
        if (!seat.getBlockOut()) {
            Booking booking = Main.bookingDAO.createCurrentBooking(seat.getSeatNo(), date);
            if (booking.getPending()) {
                booking.setPending(false);
                Main.bookingDAO.addBooking(booking);
                seatX.setFill(Color.ORANGE);
                updateSuccess.setText("Seat " + seat.getSeatNo() + " Booking Accepted");
            }
        } else {
            updateSuccess.setText("Seat COVID Locked");
        }
    }

    private void release(Rectangle seatX) throws SQLException {
        Seat seat = allSeats.get(getIndex(seatX));
        if (!seat.getBlockOut()) {

            if (seatX.getFill().equals(Color.RED)) {
                seat.unlockDate(date);
                Main.seatDAO.addSeat(seat);
                updateSuccess.setText("Seat " + seat.getSeatNo() + " Unlocked");

            } else if (seatX.getFill().equals(Color.GREEN)) {
                updateSuccess.setText("Seat " + seat.getSeatNo() + " Already Open");
            } else {
                Main.bookingDAO.deleteIncompleteBooking(seat.getSeatNo(), date);
                updateSuccess.setText("Seat " + seat.getSeatNo() + " Released");
            }

            seatX.setFill(Color.GREEN);
        } else {
            updateSuccess.setText("Seat COVID Locked");
        }
    }





}
