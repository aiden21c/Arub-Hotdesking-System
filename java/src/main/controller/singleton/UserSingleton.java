package main.controller.singleton;

import main.model.object.user.User;

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

}
