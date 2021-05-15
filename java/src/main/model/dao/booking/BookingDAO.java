package main.model.dao.booking;

import com.sun.tools.javac.util.Name;
import main.Main;
import main.model.dao.AbstractDAO;
import main.model.object.booking.Booking;
import main.model.object.seat.Seat;
import main.model.object.user.User;
import main.model.utilities.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookingDAO extends AbstractDAO {
    private enum TableValues {SEATNO, USERNAME, PENDING, DATE}

    /**
     * Adds a given booking to the database
     * @param booking the booking to be added to the database
     * @throws SQLException if any part of the booking is null, or if the username and seat number
     * do not accurately reference valid rows in the Employee or Seat tables
     */
    public void addBooking(Booking booking) throws SQLException {
        if (exists(booking)) {deleteBooking(booking);}

        assert connection != null;
        String queryString = "INSERT INTO Booking(seatno, username, pending, date) VALUES (?,?,?,DATE(?))";

        PreparedStatement ps = connection.prepareStatement(queryString);

        ps.setInt(TableValues.SEATNO.ordinal() + 1, booking.getSeat().getSeatNo());
        ps.setString(TableValues.USERNAME.ordinal() + 1, booking.getUser().getUsername());
        ps.setBoolean(TableValues.PENDING.ordinal() + 1, booking.getPending());
        ps.setString(TableValues.DATE.ordinal() + 1, booking.getDate().toString());

        ps.execute();
        ps.close();
    }

    /**
     * Creates a booking object from a row in the database
     * @param seatNo the given seat number of the booking
     * @param username the given username of the booking
     * @return a booking object with values associated with given parameters
     * @throws SQLException if a booking cannot be found with given parameters
     * @throws ClassNotFoundException
     */
    public Booking createBooking(int seatNo, String username) throws SQLException, ClassNotFoundException {
        PreparedStatement ps;
        ResultSet rs;
        String queryString = "select * from Booking where seatNo = ? and username = ?";
        ps = connection.prepareStatement(queryString);
        ps.setInt( TableValues.SEATNO.ordinal() + 1, seatNo);
        ps.setString(TableValues.USERNAME.ordinal() + 1, username);

        rs = ps.executeQuery();

        boolean pending = rs.getBoolean("pending");
        LocalDate date = Utilities.stringToDate(rs.getString("date"));

        ps.close();
        rs.close();

        Seat seat = Main.seatDAO.createSeat(seatNo);
        User user = Main.userDAO.createUser(username);

        return new Booking(seat, user, pending, date);
    }

    /**
     * Deletes a given booking from the database
     * @param booking the booking to be deleted
     * @throws SQLException
     */
    private void deleteBooking(Booking booking) throws SQLException {
        assert connection != null;

        String queryString = "delete from Booking where seatNo = ? and username = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setInt(TableValues.SEATNO.ordinal() + 1, booking.getSeat().getSeatNo());
        ps.setString(TableValues.USERNAME.ordinal() + 1, booking.getUser().getUsername());

        ps.execute();
        ps.close();
    }

    /**
     * Checks if a booking object already exists within the database
     * @param booking the booking object to test existance of
     * @return a boolean identifying the bookings existance in the database
     * @throws SQLException
     */
    private boolean exists(Booking booking) throws SQLException {
        boolean exists = false;

        ResultSet rs;
        String queryString = "SELECT COUNT(*) count FROM Booking WHERE seatNo = ? and username = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setInt(TableValues.SEATNO.ordinal() + 1, booking.getSeat().getSeatNo());
        ps.setString(TableValues.USERNAME.ordinal() + 1, booking.getUser().getUsername());

        rs = ps.executeQuery();

        int count = rs.getInt(1);

        if (count > 0) {exists = true;}

        rs.close();
        ps.close();

        return exists;
    }


}
