package main.model.dao.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Main;
import main.model.dao.AbstractDAO;
import main.model.object.booking.Seat;
import main.model.object.user.Admin;
import main.model.object.user.Employee;
import main.model.object.user.User;

public class UserDAO extends AbstractDAO {

    private enum TableValues {
        EMPID,
        FIRSTNAME,
        LASTNAME,
        ROLE,
        AGE,
        USERNAME,
        PASSWORD,
        ISADMIN
    }

    public void addUser(User user) throws SQLException {
        assert connection != null;

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

        Main.secretQuestionDAO.addSecretQuestion(user);
        Main.whiteListDAO.addWhiteList(user);
    }

    public User createUser(String user) throws SQLException, ClassNotFoundException {
        User userObject = null;
        ResultSet rs = checkLogin(user);

        int empID = rs.getInt("empID");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String role = rs.getString("role");
        int age = rs.getInt("age");
        String username = rs.getString("username");
        String password = rs.getString("password");
        boolean isAdmin = rs.getBoolean("isAdmin");

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

    private ResultSet checkLogin(String username) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        String queryString = "select * from Employee where username = ?";
        ps = connection.prepareStatement(queryString);
        ps.setString(1, username);

        rs = ps.executeQuery();
        ps.close();

        return rs;
    }


}
