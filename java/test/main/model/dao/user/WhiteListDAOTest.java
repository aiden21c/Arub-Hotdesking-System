package main.model.dao.user;

import main.Main;
import main.model.object.seat.Seat;
import main.model.object.user.Employee;
import main.model.object.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WhiteListDAOTest {

    @Test
    @DisplayName("Tests adding a whitelist to database, and retrieving it")
    public void testAddListandGetList() {
        String username = "jimmy.neutron";

        String[] secretQuestion = new String[2];
        secretQuestion[0] = "Who is your best friend";
        secretQuestion[1] = "Carl";

        ArrayList<Seat> whitelist = new ArrayList<>();
        ArrayList<LocalDate> dates = new ArrayList<>();
        dates.add(LocalDate.of(2021, 06, 15));
        whitelist.add(new Seat(6, false, dates));

        User user = new Employee(297456, "Jimmy", "Neutron", "Inventor", 12, username, "password", secretQuestion, whitelist);
        ArrayList<Seat> whitelistTest = null;

        try {
            Main.whiteListDAO.addWhiteList(user);
            whitelistTest = Main.whiteListDAO.getWhiteList(username);

        } catch (SQLException e) {
            System.out.println("Could not add or retrieve the whitelist array created from the database");
        }

        assertEquals(whitelist.size(), whitelistTest.size(), "Should be equal if arrays retrieved are equal size");

        for (int i = 0; i < whitelist.size(); i++) {
            assertTrue(whitelist.get(i).equals(whitelistTest.get(i)), "Should be equal if added to database and retrieved");
        }
    }
}