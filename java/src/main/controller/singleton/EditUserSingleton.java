package main.controller.singleton;

import main.model.object.user.User;

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

}
