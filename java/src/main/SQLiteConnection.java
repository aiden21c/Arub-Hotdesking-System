package main;
import java.sql.*;


public class SQLiteConnection {

    /**
     * Establishes a new connection to the assignment database.
     * @return a connection to the database
     * @throws SQLException if an connection cannot be established
     * @throws ClassNotFoundException if the "org.sqlite.JDBC" class cannot be found
     */
    public static Connection connect() throws SQLException, ClassNotFoundException {
      Class.forName("org.sqlite.JDBC");
      Connection connection = DriverManager.getConnection("jdbc:sqlite:assignment.db");
      return connection;
    }
}
