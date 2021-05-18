package main.model.dao.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Main;
import main.model.dao.AbstractDAO;
import main.model.object.seat.Seat;
import main.model.object.user.Admin;
import main.model.object.user.Employee;
import main.model.object.user.User;

public class UserDAO extends AbstractDAO {

    private enum TableValues {
        EMPID, FIRSTNAME, LASTNAME, ROLE, AGE, USERNAME, PASSWORD, ISADMIN
    }

    /**
     * Add a user to the database
     *      If there is already an entry in the table for this username, the method identifies this
     *      as an "update values" and removes the current entry from the database before adding the new one
     * @param user the user object to add to the database
     * @throws SQLException
     */
    public void addUser(User user) throws SQLException {
        assert connection != null;
        boolean exists = true;

        try {
            exists(user.getUsername());
        } catch (SQLException throwables) {
            exists = false;
        }

        if (exists) {deleteUser(user.getUsername());}

        String queryString = "INSERT INTO Employee(empID, firstName, lastName, role, age, username, " +
        "password, isAdmin) VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setInt(TableValues.EMPID.ordinal() + 1, user.getEmpID());
        ps.setString(TableValues.FIRSTNAME.ordinal() + 1, user.getFirstName());
        ps.setString(TableValues.LASTNAME.ordinal() + 1, user.getLastName());
        ps.setString(TableValues.ROLE.ordinal() + 1, user.getRole());
        ps.setInt(TableValues.AGE.ordinal() + 1, user.getAge());
        ps.setString(TableValues.USERNAME.ordinal() + 1, user.getUsername());
        ps.setString(TableValues.PASSWORD.ordinal() + 1, user.getPassword());
        ps.setBoolean(TableValues.ISADMIN.ordinal() + 1, !(user instanceof Employee));

        ps.execute();
        ps.close();

        Main.secretQuestionDAO.addSecretQuestion(user, exists);
        Main.whiteListDAO.addWhiteList(user);
    }

    /**
     * Creates a user object from a row in the database
     * @param username the username of the user object to create
     * @return the user object created from database values
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public User createUser(String username) throws SQLException, ClassNotFoundException {
        User userObject;
        ResultSet rs;
        String queryString = "select * from Employee where username = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setString(1, username);

        rs = ps.executeQuery();

        int empID = rs.getInt("empID");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String role = rs.getString("role");
        int age = rs.getInt("age");
        String password = rs.getString("password");
        boolean isAdmin = rs.getBoolean("isAdmin");

        rs.close();
        ps.close();

        String[] secretQuestion = Main.secretQuestionDAO.getTable(username);
        ArrayList<Seat> whitelist = Main.whiteListDAO.getWhiteList(username);


        if (!isAdmin) {
            userObject = new Employee(empID, firstName, lastName, role, age, username, password,
                    secretQuestion, whitelist);

        } else {
            userObject = new Admin(empID, firstName, lastName, role, age, username, password,
                    secretQuestion, whitelist);
        }

        return userObject;
    }

    /**
     * Checks if an entry exists in the system for the given username
     * @param username the username to check in the database
     * @return boolean identifying whether or not this user exists in the database
     * @throws SQLException
     */
    private boolean exists(String username) throws SQLException {
        boolean exists = false;

        ResultSet rs;
        String queryString = "SELECT COUNT(*) count FROM Employee WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setString(1, username);

        rs = ps.executeQuery();

        int count = rs.getInt(1);

        if (count > 0) {exists = true;}

        rs.close();
        ps.close();

        return exists;
    }

    /**
     * Deletes a user from the database
     *      First deletes values from the booking, whitelist and secret question table, before deleting user
     * @param username the username of the user to delete from the database
     * @throws SQLException
     */
    private void deleteUser(String username) throws SQLException {
        Main.secretQuestionDAO.deleteSecretQuestion(username);
        Main.whiteListDAO.deleteWhiteList(username);
        Main.bookingDAO.deleteBooking(username);

        assert connection != null;

        String queryString = "delete from Employee where username = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setString(1, username);

        ps.execute();
        ps.close();
    }

}
