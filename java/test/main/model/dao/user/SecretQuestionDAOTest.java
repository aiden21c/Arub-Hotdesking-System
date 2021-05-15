package main.model.dao.user;

import main.Main;
import main.model.object.seat.Seat;
import main.model.object.user.Employee;
import main.model.object.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SecretQuestionDAOTest {

    @BeforeEach
    public void clearDatabase() {
        try {
            Main.secretQuestionDAO.deleteSecretQuestion("jimmy.neutron");
        } catch (SQLException ignored) { }
    }

    @Test
    @DisplayName("Tests adding a secret question to database, and retrieving it")
    public void testAddQuestionandGetQuestion() throws SQLException {
        String username = "jimmy.neutron";

        String[] secretQuestion = new String[2];
        secretQuestion[0] = "Who is your best friend";
        secretQuestion[1] = "Carl";

        ArrayList<Seat> whitelist = new ArrayList<>();


        User user = new Employee(297456, "Jimmy", "Neutron", "Inventor", 12, username, "password", secretQuestion, whitelist);

        Main.secretQuestionDAO.addSecretQuestion(user, false);

        String[] secretQuestionTest = Main.secretQuestionDAO.getTable(username);

        assertEquals(secretQuestion[0], secretQuestionTest[0], "Should be equal if added to database and retrieved");
        assertEquals(secretQuestion[1], secretQuestionTest[1], "Should be equal if added to database and retrieved");

    }

}