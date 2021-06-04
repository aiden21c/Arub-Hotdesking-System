package main.model.dao.seat;

import main.Main;
import main.model.dao.AbstractDAO;
import main.model.object.seat.Seat;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SeatDAO extends AbstractDAO {
    private enum TableValues {SEATNO, BLOCKOUT}

    /**
     * Adds a given seat object to the database, along with the seats blockOut dates arraylist
     *      If seatNo already exists in the database, the method identifies this as a "value update"
     *      The seat will first be removed from the database, and then re-added
     * @param seat The given seat to add to the database
     * @throws SQLException if any of the values of the given seat is null
     */
    public void addSeat(Seat seat) throws SQLException {
        if (exists(seat.getSeatNo())) {
            delete(seat);
        }

        assert connection != null;
        String queryString = "INSERT INTO Seat(seatNo, blockOut) VALUES (?,?)";

        PreparedStatement ps = connection.prepareStatement(queryString);

        ps.setInt(TableValues.SEATNO.ordinal() + 1, seat.getSeatNo());
        ps.setBoolean(TableValues.BLOCKOUT.ordinal() + 1, seat.getBlockOut());

        ps.execute();
        ps.close();

        Main.blockOutDAO.addDates(seat);
    }

    /**
     * Creates a new seat object from an entry in the database
     * @param seatNo the seat number of the seat to be created
     * @return a seat object from an entry in the database
     * @throws SQLException if no seat with this seat number exists in the database
     */
    public Seat createSeat(int seatNo) throws SQLException {
        assert connection != null;
        PreparedStatement ps;
        ResultSet rs;
        String queryString = "select blockOut from Seat where seatNo = ?";
        ps = connection.prepareStatement(queryString);

        ps.setInt(1, seatNo);
        rs = ps.executeQuery();

        boolean blockOut = rs.getBoolean("blockOut");

        ps.close();
        rs.close();

        ArrayList<LocalDate> getBlockOut = Main.blockOutDAO.createDates(seatNo);

        return new Seat(seatNo, blockOut, getBlockOut);
    }

    /**
     * Checks if a seat with the given seat number exists within the database
     * @param seatNo the seat number to be checked against database entries
     * @return a boolean identifying the existence of an entry with this seat number
     * @throws SQLException
     */
    private boolean exists(int seatNo) throws SQLException {
        boolean exists = false;

        ResultSet rs;
        String queryString = "SELECT COUNT(*) count FROM Seat WHERE seatNo = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setInt(1, seatNo);

        rs = ps.executeQuery();

        int count = rs.getInt(1);

        if (count > 0) {exists = true;}

        rs.close();
        ps.close();

        return exists;
    }

    /**
     * Deletes a given seat from the database
     *      Includes deleting all entries with this seat number in the BlockOut table
     * @param seat the seat to be deleted
     * @throws SQLException if a seat with this seat number cannot be found
     */
    private void delete(Seat seat) throws SQLException {
        Main.blockOutDAO.deleteBlockOut(seat.getSeatNo());
        Main.bookingDAO.deleteBooking(seat.getSeatNo());

        assert connection != null;

        String queryString = "delete from Seat where seatNo = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setInt(1, seat.getSeatNo());

        ps.execute();
        ps.close();

    }

    /**
     * Obtain an array list of all seats in the database
     * @return an array list of all seats
     * @throws SQLException
     */
    public ArrayList<Seat> getAllSeats() throws SQLException {
        assert connection != null;
        ArrayList<Seat> seats = new ArrayList<>();
        String queryString = "select seatNo from Seat order by seatNo asc";
        PreparedStatement ps = connection.prepareStatement(queryString);

        ResultSet rs = ps.executeQuery();

        ArrayList<Integer> seatNo = new ArrayList<>();

        while (rs.next()) {
            seatNo.add(rs.getInt(1));
        }

        rs.close();
        ps.close();

        for (Integer integer : seatNo) {
            seats.add(Main.seatDAO.createSeat(integer));
        }
        return seats;
    }

    /**
     * Sets the normal conditions of the office, allowing all seats to be booked
     * @throws SQLException
     */
    public void setNormalConditions() throws SQLException {
        assert connection != null;

        String stringQuery = "UPDATE Seat SET blockOut = ?";
        PreparedStatement ps = connection.prepareStatement(stringQuery);
        ps.setBoolean(1, false);

        ps.execute();
        ps.close();
    }

    /**
     * Sets the Covid conditions of the office, allowing every second seat to be booked
     * @throws SQLException
     */
    public void setCovidConditions() throws SQLException {
        assert connection != null;

        String stringQuery = "UPDATE Seat SET blockOut = ? where (seatNo % 2) = 0";
        PreparedStatement ps = connection.prepareStatement(stringQuery);
        ps.setBoolean(1, true);

        ps.execute();
        ps.close();
    }

    /**
     * Sets the lockdown conditions of the office, allowing no seat to be booked
     * @throws SQLException
     */
    public void lockDown() throws SQLException {
        assert connection != null;

        String stringQuery = "UPDATE Seat SET blockOut = ?";
        PreparedStatement ps = connection.prepareStatement(stringQuery);
        ps.setBoolean(1, true);

        ps.execute();
        ps.close();
    }

    public void export() throws SQLException, IOException {
        super.export("Seat");
    }
}
