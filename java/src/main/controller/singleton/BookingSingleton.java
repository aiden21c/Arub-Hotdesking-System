package main.controller.singleton;

import main.Main;
import main.model.object.booking.Booking;
import main.model.object.user.User;

import java.sql.SQLException;

/**
 * A Singleton class used to represent a singular booking to be accessed from within multiple Controllers
 */
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

    /**
     * Writes the current booking to the SQL database
     * @throws SQLException
     */
    public void writeToDatabase() throws SQLException {
        Main.bookingDAO.addBooking(this.booking);
    }

    /**
     * Checks if a given user has a current booking in the database
     *      If successful, sets the current booking instance to the found booking
     * @param user the user to check for bookings for
     * @throws SQLException thrown if no booking current exists in the database for this user
     * @throws ClassNotFoundException
     */
    public void searchBooking(User user) throws SQLException, ClassNotFoundException {
        this.booking = Main.bookingDAO.createCurrentBooking(user);
    }

    /**
     * Deletes the current booking from the database and sets the current booking to null
     * @throws SQLException
     */
    public void deleteBooking() throws SQLException {
        Main.bookingDAO.deleteBooking(this.booking);
        this.booking = null;
    }

}
