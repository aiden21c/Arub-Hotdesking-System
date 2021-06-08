package main.model.object.user;

import main.model.object.seat.Seat;

import java.sql.SQLException;
import java.util.ArrayList;

public class Admin extends User {
    /**
     * Creates a new employee from a pre-existing database entrie
     */
    public Admin(int empID, String firstName, String lastName, String role, int age,
                 String username, String password, String[] secretQuestion, ArrayList<Seat> whitelist) {

        super(empID, firstName, lastName, role, age, username, password, secretQuestion, whitelist);
    }

    /**
     * Creates a new employee from scratch
     */
    public Admin(int empID, String firstName, String lastName, String role, int age,
                       String username, String password, String[] secretQuestion) throws SQLException {
        super(empID, firstName, lastName, role, age, username, password, secretQuestion);
    }
}
