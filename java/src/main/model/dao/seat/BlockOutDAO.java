package main.model.dao.seat;

import main.model.dao.AbstractDAO;
import main.model.object.seat.Seat;
import main.model.utilities.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BlockOutDAO extends AbstractDAO {
    private enum TableValues {SEATNO, DATE}

    protected void addDates(Seat seat) throws SQLException, ClassNotFoundException {
        assert connection != null;
        PreparedStatement ps = null;

        for (LocalDate date : seat.getBlockOutDates()) {

            String queryString = "INSERT OR IGNORE INTO BlockOut(seatNo, date) VALUES (?,DATE(?))";
            ps = connection.prepareStatement(queryString);
            ps.setInt( TableValues.SEATNO.ordinal() + 1, seat.getSeatNo());

            ps.setString(TableValues.DATE.ordinal() + 1, date.toString());
            ps.execute();
        }

        assert ps != null;
        ps.close();
    }


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
}
