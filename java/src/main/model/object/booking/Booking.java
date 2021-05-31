package main.model.object.booking;

import main.model.object.seat.Seat;
import main.model.object.user.User;

import java.sql.SQLException;
import java.time.LocalDate;

public class Booking {
    private Seat seat;
    private User user;
    private boolean pending;
    private LocalDate date;

    /**
     * Constructor to create a new booking from UI. Sets pending to true
     */
    public Booking(Seat seat, User user, LocalDate date) {
        this.seat = seat;
        this.user = user;
        setPending(true);
        this.date = date;
    }

    /**
     * Constructor to create a new booking from database
     */
    public Booking(Seat seat, User user, boolean pending, LocalDate date) {
        this.seat = seat;
        this.user = user;
        setPending(pending);
        this.date = date;
    }

    public Seat getSeat() {return seat;}

    public User getUser() {return user;}

    public boolean getPending() {return pending;}

    public void setPending(boolean pending) {this.pending = pending;}

    public LocalDate getDate() {return date;}

    /**
     * Takes in a booking, and checks if the values of the booking given are equal to it's own instance values
     * @param b the booking object to be compared against
     * @return boolean identifying whether the given booking object has equal values
     */
    public boolean equals(Booking b) {
        boolean equals = false;

        if (seat.equals(b.seat)) {
            if (user.equals(b.user)) {
                if (pending == b.pending) {
                    if (date.equals(b.date)) {
                        equals = true;
                    }
                }
            }
        }

        return equals;
    }

    /**
     * Updates the user's whitelist
     */
    public void checkIn() throws SQLException {
        user.updateWhiteList(seat);
    }
}