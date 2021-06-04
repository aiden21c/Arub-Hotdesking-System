package main.model.dao.seat;

import main.model.dao.AbstractDAO;
import main.model.object.seat.Seat;
import main.model.utilities.Utilities;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BlockOutDAO extends AbstractDAO {
    private enum TableValues {SEATNO, DATE}

    /**
     * Adds a given seat's blockOut array to the database to the database
     * If date already exists for given seat, the entry is ignored
     * @param seat the seat who's blockOut array is to be added
     * @throws SQLException
     */
    protected void addDates(Seat seat) throws SQLException {
        assert connection != null;

        for (LocalDate date : seat.getBlockOutDates()) {

            String queryString = "INSERT OR IGNORE INTO BlockOut(seatNo, date) VALUES (?,DATE(?))";
            PreparedStatement ps = connection.prepareStatement(queryString);
            ps.setInt( TableValues.SEATNO.ordinal() + 1, seat.getSeatNo());

            ps.setString(TableValues.DATE.ordinal() + 1, date.toString());
            ps.execute();
            ps.close();
        }
    }

    /**
     * Creates an arraylist of dates that a given seat is blocked out for
     * @param seatNo the seat number who's blocked out dates are to be retrieved
     * @return arraylist of blocked out dates
     * @throws SQLException
     */
    protected ArrayList<LocalDate> createDates(int seatNo) throws SQLException {
        ArrayList<LocalDate> dates = new ArrayList<>();

        assert connection != null;
        PreparedStatement ps;
        ResultSet rs;
        String queryString = "select date from BlockOut where seatNo = ?";
        ps = connection.prepareStatement(queryString);

        ps.setInt(1, seatNo);

        rs = ps.executeQuery();
        while (rs.next()) {
            LocalDate date = Utilities.stringToDate(rs.getString(1));
            dates.add(date);
        }

        rs.close();
        ps.close();

        return dates;
    }

    /**
     * Deletes an entry from the BlockOut table for a given date and seat number
     * @param seatNo the seat number associated with the entry
     * @param date the entry's date to be deleted
     * @throws SQLException if there is no entry in the database for the given primary key
     */
    protected void deleteBlockOut(int seatNo, LocalDate date) throws SQLException {
        String queryString = "DELETE FROM BlockOut WHERE seatNo = ? and date = DATE(?)";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setInt( TableValues.SEATNO.ordinal() + 1, seatNo);
        ps.setString(TableValues.DATE.ordinal() + 1, date.toString());
        ps.execute();
        ps.close();
    }

    /**
     * Deletes all blockout dates for a given seat number
     * @param seatNo the seat number to delete all entries for
     * @throws SQLException if no entries exist for the given seat number
     */
    protected void deleteBlockOut(int seatNo) throws SQLException {
        String queryString = "DELETE FROM BlockOut WHERE seatNo = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setInt( TableValues.SEATNO.ordinal() + 1, seatNo);
        ps.execute();
        ps.close();
    }

    public void export() throws SQLException, IOException {
        super.export("BlockOut");
    }

}
