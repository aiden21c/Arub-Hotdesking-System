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

public class UserDAOTest {
    @Test
    @DisplayName("Tests adding a user to database, and retrieving it")
    public void testAddUserandGetUser() {
        String username = "jimmy.neutron";

        String[] secretQuestion = new String[2];
        secretQuestion[0] = "Who is your best friend";
        secretQuestion[1] = "Carl";

        ArrayList<Seat> whitelist = new ArrayList<>();
        ArrayList<LocalDate> dates = new ArrayList<>();
        dates.add(LocalDate.of(2021, 06, 15));
        whitelist.add(new Seat(6, false, dates));

        User user = new Employee(297456, "Jimmy", "Neutron", "Inventor", 12, username, "password", secretQuestion, whitelist);
        User userTest = null;

        try {
            Main.userDAO.addUser(user);
            userTest = Main.userDAO.createUser(username);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Could not add or retrieve the user object created from the database");
        }

        assertTrue(user.equals(userTest), "Should be equal if added to database and retrieved");
    }

}