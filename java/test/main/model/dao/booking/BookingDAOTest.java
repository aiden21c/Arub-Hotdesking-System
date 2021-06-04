package main.model.dao.booking;

import main.Main;
import main.model.object.booking.Booking;
import main.model.object.seat.Seat;
import main.model.object.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BookingDAOTest {

    @Test
    @DisplayName("Should accurately add booking to the database")
    void testBookingandGetBooking() {
        String username = "ned.flanders"; int seatNo = 1;

        User user = null;
        Seat seat = null;

        try {
            user = Main.userDAO.createUser(username);
            seat = Main.seatDAO.createSeat(seatNo);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("User or seat does not exist in the database for the given information");
        }

        LocalDate date = LocalDate.of(2021, 07, 02);

        Booking booking = new Booking(seat, user, date);
        Booking bookingTest = null;

        try {
            Main.bookingDAO.addBooking(booking);
            bookingTest = Main.bookingDAO.createCurrentBooking(username);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Could not add or retrieve the booking object created from the database");
        }

        assertTrue(booking.equals(bookingTest), "Should be equal if added to database and retrieved");

    }

}