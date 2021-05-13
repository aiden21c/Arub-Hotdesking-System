package main.model.object.booking;

import main.model.object.seat.Seat;
import main.model.object.user.User;

import java.time.LocalDate;

public class Booking {
    private Seat seat;
    private User user;
    private boolean pending;
    private LocalDate date;

    public Booking(Seat seat, User user, boolean pending, LocalDate date) {
        this.seat = seat;
        this.user = user;
        this.pending = pending;
        this.date = date;
    }

    public Seat getSeat() {return seat;}

    public User getUser() {return user;}

    public boolean getPending() {return pending;}

    public LocalDate getDate() {return date;}
}
