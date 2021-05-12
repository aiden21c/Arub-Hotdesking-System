package main.model.object.user;

import main.model.object.booking.Seat;

import java.util.ArrayList;

public class Admin extends User {
    public Admin(int empID, String firstName, String lastName, String role, int age,
                 String username, String password, String[] secretQuestion, ArrayList<Seat> whitelist) {

        super(empID, firstName, lastName, role, age, username, password, secretQuestion, whitelist);
    }
}
