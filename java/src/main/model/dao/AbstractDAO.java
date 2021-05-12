package main.model.dao;

import main.SQLiteConnection;

import java.sql.Connection;
import java.sql.SQLException;


public abstract class AbstractDAO {
    protected Connection connection;

    /**
     * Constructor attempts to connect to the SQLite server.
     * If connection cannot be established, the program shall terminate.
     */
    public AbstractDAO() {
        try {
            connection = SQLiteConnection.connect();
        } catch (SQLException | ClassNotFoundException throwables) {
            System.exit(1);
        }
    }

    /**
     * Checks whether the database is currently connected
     * @return true if the database is connected currently. Returns false if there is no database connected.
     */
    public boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException exception) {
            return false;
        }
    }
}
