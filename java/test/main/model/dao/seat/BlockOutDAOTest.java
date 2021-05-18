package main.model.dao.seat;

import main.Main;
import main.model.object.seat.Seat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BlockOutDAOTest {

    @Test
    @DisplayName("Should accurately add dates to the database")
    void testAddDatesAndGetDates() {
        int seatNo = 1;
        ArrayList<LocalDate> blockDates = new ArrayList<>();
        blockDates.add(LocalDate.of(2021, 06, 14));
        blockDates.add(LocalDate.of(2021, 07, 15));
        Seat seat = new Seat(seatNo, false, blockDates);

        ArrayList<LocalDate> blockDatesTest = null;

        try {
            Main.blockOutDAO.addDates(seat);
            blockDatesTest = Main.blockOutDAO.createDates(seatNo);
        } catch (SQLException e) {
            System.out.println("Could not add or retrieve the blockout array created from the database");
        }

        assertEquals(blockDates, blockDatesTest, "Should be equal if added to database and retrieved");
    }
}