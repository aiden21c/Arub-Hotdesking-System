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
    void testBookingandGetBooking() throws SQLException, ClassNotFoundException {
        String username = "bob.belcher"; int seatNo = 1;

        User user = Main.userDAO.createUser(username);
        Seat seat = Main.seatDAO.createSeat(seatNo);

        LocalDate date = LocalDate.of(2021, 06, 02);

        Booking booking = new Booking(seat, user, true, date);

        Main.bookingDAO.addBooking(booking);

        Booking bookingTest = Main.bookingDAO.createBooking(seatNo, username);

        assertTrue(booking.equals(bookingTest), "Should be equal if added to database and retrieved");

    }

}