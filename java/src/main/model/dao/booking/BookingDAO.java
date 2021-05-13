package main.model.dao.booking;

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

    public void addBooking(Booking booking) throws SQLException {
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
}
