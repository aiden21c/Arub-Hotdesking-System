package main.controller.singleton;

import main.Main;
import main.model.object.user.User;

import java.sql.SQLException;

/**
 * A singleton to be used to hold a user that is currently being edited by an admin
 */
public final class EditUserSingleton {

    private User user;
    private final static EditUserSingleton INSTANCE = new EditUserSingleton();

    private EditUserSingleton() { }

    public static EditUserSingleton getInstance() {
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
     * Deletes the current user from the database
     * @param updateBool defines whether the deletion is an update or a deletion
     * @throws SQLException
     */
    public void deleteUser(boolean updateBool) throws SQLException {
        Main.userDAO.deleteUser(this.user.getUsername(), updateBool);
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
