package main.model.dao.seat;

import main.Main;
import main.model.object.booking.Seat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SeatDAOTest {

    @Test
    @DisplayName("Should add a new seat to the database, and retrieve the seat from the database")
    void testAddSeatandGetSeat() throws SQLException, ClassNotFoundException {
        int seatNo = 7;
        ArrayList<LocalDate> blockDates = new ArrayList<>();
        blockDates.add(LocalDate.of(2021, 06, 14));
        blockDates.add(LocalDate.of(2021, 07, 15));
        Seat seat = new Seat(seatNo, false, blockDates);

        Main.seatDAO.addSeat(seat);
        Seat seatTest = Main.seatDAO.createSeat(seatNo);

        assertEquals(seat, seatTest, "Should be equal if added to database and retrieved");
    }
}