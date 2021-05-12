package main.model.interfacemodel;

import main.Main;
import main.SQLiteConnection;
import main.model.object.user.User;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginModel {

    Connection connection;

    public LoginModel(){

        try {
            connection = SQLiteConnection.connect();
        } catch (SQLException throwables) {
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.exit(1);
        }
    }

    public Boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    public Boolean isLogin(String user, String pass) throws SQLException {
        boolean login = false;
        User userObject = Main.userDAO.createUser(user);

        if (pass.equals(userObject.getPassword())) {
            login = true;
        }

        return login;
    }
}
