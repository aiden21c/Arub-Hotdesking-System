package main.controller.singleton;

import main.Main;
import main.model.object.user.User;

import java.sql.SQLException;

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

    public void writeToDatabase() throws SQLException {
        Main.userDAO.addUser(this.user);
    }

    public void searchUser(String username) throws SQLException, ClassNotFoundException {
        this.user = Main.userDAO.createUser(username);
    }

}
