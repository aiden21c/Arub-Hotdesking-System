package main.model.dao.seat;

import main.Main;
import main.model.object.seat.Seat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SeatDAOTest {

    @Test
    @DisplayName("Should add a new seat to the database, and retrieve the seat from the database")
    void testAddSeatandGetSeat() {
        int seatNo = 7;
        ArrayList<LocalDate> blockDates = new ArrayList<>();
        blockDates.add(LocalDate.of(2021, 06, 14));
        blockDates.add(LocalDate.of(2021, 07, 15));
        Seat seat = new Seat(seatNo, false, blockDates);

        Seat seatTest = null;

        try {
            Main.seatDAO.addSeat(seat);
            seatTest = Main.seatDAO.createSeat(seatNo);
        } catch (SQLException e) {
            System.out.println("Could not add or retrieve the seat object created from the database");
        }

        assertTrue(seat.equals(seatTest), "Should be equal if added to database and retrieved");
    }
}