package main.controller.singleton;

import main.Main;
import main.model.object.user.User;

import java.sql.SQLException;

/**
 * A singleton to be used to hold a user that is currently logged in
 */
public final class UserSingleton {

    private User user;
    private final static UserSingleton INSTANCE = new UserSingleton();

    private UserSingleton() { }

    public static UserSingleton getInstance() {
        return INSTANCE;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    /**
     * Writes the current user to the database
     * @throws SQLException
     */
    public void writeToDatabase() throws SQLException {
        Main.userDAO.addUser(this.user);
    }

    /**
     * Searches the database for a user with the given username
     * @param username the username given
     * @throws SQLException if a user cannot be found matching the given username
     * @throws ClassNotFoundException
     */
    public void searchUser(String username) throws SQLException, ClassNotFoundException {
        this.user = Main.userDAO.createUser(username);
    }

}
