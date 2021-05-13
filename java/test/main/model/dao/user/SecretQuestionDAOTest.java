package main.model.dao.user;

import main.Main;
import main.model.object.booking.Seat;
import main.model.object.user.Employee;
import main.model.object.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SecretQuestionDAOTest {

    @Test
    @DisplayName("Tests adding a secret question to databae, and retrieving it")
    public void testAddQuestionandGetQuestion() throws SQLException {
        String username = "jimmy.neutron";

        String[] secretQuestion = new String[2];
        secretQuestion[0] = "Who is your best friend";
        secretQuestion[1] = "Carl";

        ArrayList<Seat> whitelist = new ArrayList<>();


        User user = new Employee(297456, "Jimmy", "Neutron", "Inventor", 12, username, "password", secretQuestion, whitelist);

        Main.secretQuestionDAO.addSecretQuestion(user);

        String[] secretQuestionTest = Main.secretQuestionDAO.getTable(username);

        assertEquals(secretQuestion, secretQuestionTest, "Should be equal if added to database and retrieved");
    }

}