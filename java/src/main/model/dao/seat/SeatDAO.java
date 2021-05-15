package main.model.dao.seat;

import main.Main;
import main.model.dao.AbstractDAO;
import main.model.object.seat.Seat;

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
        assert connection != null;

        String queryString = "delete from Seat where seatNo = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setInt(1, seat.getSeatNo());

        ps.execute();
        ps.close();

        Main.blockOutDAO.deleteBlockOut(seat.getSeatNo());
    }
}
