package main.controller.singleton;

import main.Main;
import main.model.object.user.User;

import java.sql.SQLException;

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

    public void writeToDatabase() throws SQLException {
        Main.userDAO.addUser(this.user);
    }

    public void deleteUser() throws SQLException {
        Main.userDAO.deleteUser(this.user.getUsername());
    }

    public void searchUser(String username) throws SQLException, ClassNotFoundException {
        this.user = Main.userDAO.createUser(username);
    }

}
