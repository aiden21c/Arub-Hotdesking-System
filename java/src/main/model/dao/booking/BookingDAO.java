package main.model.dao.booking;

import main.Main;
import main.model.dao.AbstractDAO;
import main.model.object.booking.Booking;
import main.model.object.seat.Seat;
import main.model.object.user.User;
import main.model.utilities.Utilities;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookingDAO extends AbstractDAO {

    private enum TableValues {SEATNO, USERNAME, PENDING, DATE, COMPLETED}

    /**
     * Adds a given booking to the database
     * @param booking the booking to be added to the database
     * @throws SQLException if any part of the booking is null, or if the username and seat number
     * do not accurately reference valid rows in the Employee or Seat tables
     */
    public void addBooking(Booking booking) throws SQLException {
        if (exists(booking)) {deleteBooking(booking);}

        assert connection != null;
        String queryString = "INSERT INTO Booking(seatno, username, pending, date, completed) VALUES (?,?,?,DATE(?), ?)";

        PreparedStatement ps = connection.prepareStatement(queryString);

        ps.setInt(TableValues.SEATNO.ordinal() + 1, booking.getSeat().getSeatNo());
        ps.setString(TableValues.USERNAME.ordinal() + 1, booking.getUser().getUsername());
        ps.setBoolean(TableValues.PENDING.ordinal() + 1, booking.getPending());
        ps.setString(TableValues.DATE.ordinal() + 1, booking.getDate().toString());
        ps.setBoolean(TableValues.COMPLETED.ordinal() + 1, booking.getCompleted());

        ps.execute();
        ps.close();
    }

    /**
     * Creates a booking object from a row in the database
     * @param user the given username of the booking
     * @return a booking object with values associated with given parameters
     * @throws SQLException if a booking cannot be found with given parameters
     * @throws ClassNotFoundException
     */
    public Booking createCurrentBooking(User user) throws SQLException, ClassNotFoundException {
        PreparedStatement ps;
        ResultSet rs;
        String queryString = "select * from Booking where username = ? and completed = ?";
        ps = connection.prepareStatement(queryString);
        ps.setString(1, user.getUsername());
        ps.setBoolean(2, false);

        rs = ps.executeQuery();

        int seatNo = rs.getInt("seatNo");
        boolean pending = rs.getBoolean("pending");
        LocalDate date = Utilities.stringToDate(rs.getString("date"));
        boolean completed = rs.getBoolean("completed");

        ps.close();
        rs.close();

        Seat seat = Main.seatDAO.createSeat(seatNo);

        return new Booking(seat, user, pending, date, completed);
    }

    /**
     * Creates a booking object from a row in the database
     * @param seatNo the given seat number of the booking
     * @param date the given date of the booking
     * @return a booking object with values associated with given parameters
     * @throws SQLException if a booking cannot be found with given parameters
     * @throws ClassNotFoundException
     */
    public Booking createCurrentBooking(int seatNo, LocalDate date) throws SQLException, ClassNotFoundException {
        PreparedStatement ps;
        ResultSet rs;
        String queryString = "select * from Booking where seatNo = ? and date = DATE(?) and completed = ?";
        ps = connection.prepareStatement(queryString);
        ps.setInt( 1, seatNo);
        ps.setString(2, date.toString());
        ps.setBoolean(3, false);

        rs = ps.executeQuery();

        String username = rs.getString("username");
        boolean pending = rs.getBoolean("pending");
        boolean completed = rs.getBoolean("completed");

        ps.close();
        rs.close();

        Seat seat = Main.seatDAO.createSeat(seatNo);
        User user = Main.userDAO.createUser(username);

        return new Booking(seat, user, pending, date, completed);
    }

    /**
     * Deletes a given booking from the database
     * @param booking the booking to be deleted
     * @throws SQLException
     */
    public void deleteBooking(Booking booking) throws SQLException {
        assert connection != null;

        String queryString = "delete from Booking where seatNo = ? and username = ? and date = DATE(?)";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setInt(TableValues.SEATNO.ordinal() + 1, booking.getSeat().getSeatNo());
        ps.setString(TableValues.USERNAME.ordinal() + 1, booking.getUser().getUsername());
        ps.setString(3, booking.getDate().toString());

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

        assert connection != null;

        ResultSet rs;
        String queryString = "SELECT COUNT(*) count FROM Booking WHERE seatNo = ? and username = ? and date = DATE(?)";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setInt(TableValues.SEATNO.ordinal() + 1, booking.getSeat().getSeatNo());
        ps.setString(TableValues.USERNAME.ordinal() + 1, booking.getUser().getUsername());
        ps.setString(3, booking.getDate().toString());

        rs = ps.executeQuery();

        int count = rs.getInt(1);

        if (count > 0) {exists = true;}

        rs.close();
        ps.close();

        return exists;
    }

    /**
     * Deletes all bookings for a given user
     * @param username the username for the user to delete all bookings for
     * @throws SQLException
     */
    public void deleteBooking(String username) throws SQLException {
        assert connection != null;

        String queryString = "delete from Booking where username = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setString(TableValues.USERNAME.ordinal(), username);

        ps.execute();
        ps.close();
    }

    /**
     * Deletes all bookings for a given seat
     * @param seatNo the seat number for the seat to delete all bookings for
     * @throws SQLException
     */
    public void deleteBooking(int seatNo) throws SQLException {
        assert connection != null;

        String queryString = "delete from Booking where seatNo = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setInt(TableValues.SEATNO.ordinal() + 1, seatNo);

        ps.execute();
        ps.close();
    }

    /**
     * Checks if a seat is booked on a given date
     * @param seatNo the seat to check if booked
     * @param date the date to check if seat is booked on
     * @return boolean identifying if the seat is booked for a given date
     * @throws SQLException
     */
    public boolean isBooked(int seatNo, LocalDate date) throws SQLException {
        boolean booked = false;

        assert connection != null;

        ResultSet rs;
        String queryString = "SELECT COUNT(*) count FROM Booking WHERE seatNo = ? and date = DATE(?)";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setInt(1, seatNo);
        ps.setString(2, date.toString());

        rs = ps.executeQuery();

        int count = rs.getInt(1);

        if (count > 0) {booked = true;}

        rs.close();
        ps.close();

        return booked;
    }

    /**
     * Checks if a given user has any current bookings
     * @param username the username of the user to check
     * @return boolean identifying if the user has any current bookings
     * @throws SQLException
     */
    public boolean checkUser(String username) throws SQLException {
        boolean hasBooking = false;

        assert connection != null;

        ResultSet rs;
        String queryString = "SELECT COUNT(*) count FROM Booking WHERE username = ? and completed = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setString(1, username);
        ps.setBoolean(2, false);

        rs = ps.executeQuery();

        int count = rs.getInt(1);

        if (count > 0) {hasBooking = true;}

        rs.close();
        ps.close();

        return hasBooking;
    }

    /**
     * Deletes all bookings which have not been confirmed within 24 hours of commencement
     * @throws SQLException
     */
    public void deleteUnconfirmedBookings() throws SQLException {
        LocalDate date = LocalDate.now();
        LocalDate lt = date.plusDays(2);

        assert connection != null;
        String queryString = "DELETE from Booking where pending = ? and date < DATE(?) and completed = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);

        ps.setBoolean(1, true);
        ps.setString(2, lt.toString());
        ps.setBoolean(3, false);

        ps.execute();
        ps.close();
    }

    /**
     * Deletes the bookings based on covid conditions.
     *      All seats will need to be rebooked for all future dates
     * @throws SQLException
     */
    public void setCovidConditions() throws SQLException {
        LocalDate date = LocalDate.now();

        assert connection != null;
        String queryString = "DELETE from Booking where date > DATE(?)";
        PreparedStatement ps = connection.prepareStatement(queryString);

        ps.setString(1, date.toString());

        ps.execute();
        ps.close();
    }

    /**
     * Deletes all bookings which have not been completed since the booking occured
     * @throws SQLException
     */
    public void deleteIncompleteBookings() throws SQLException {
        LocalDate date = LocalDate.now();

        assert connection != null;
        String queryString = "DELETE from Booking where date < DATE(?) and completed = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);

        ps.setString(1, date.toString());
        ps.setBoolean(2, false);

        ps.execute();
        ps.close();
    }

    public void export() throws SQLException, IOException {
        // TODO test this order by works
        super.export("Booking ORDER BY date, seatNo");
    }


}
