package main.controller.singleton;

import main.Main;
import main.model.object.booking.Booking;
import main.model.object.user.User;

import java.sql.SQLException;

public class BookingSingleton {

    private Booking booking;
    private final static BookingSingleton INSTANCE = new BookingSingleton();

    private BookingSingleton() { }

    public static BookingSingleton getInstance() {
        return INSTANCE;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return this.booking;
    }

    public void writeToDatabase() throws SQLException {
        Main.bookingDAO.addBooking(this.booking);
    }

    public void searchBooking(User user) throws SQLException, ClassNotFoundException {
        this.booking = Main.bookingDAO.createCurrentBooking(user);
    }

    public void deleteBooking() throws SQLException {
        Main.bookingDAO.deleteBooking(this.booking);
    }

}
