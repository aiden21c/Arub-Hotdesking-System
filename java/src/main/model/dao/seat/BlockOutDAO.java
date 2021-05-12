package main.model.dao.seat;

import main.model.dao.AbstractDAO;
import main.model.object.booking.Seat;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BlockOutDAO extends AbstractDAO {
    private enum TableValues {SEATNO, DATE}

    protected void addDates(Seat seat) throws SQLException {
        assert connection != null;
        PreparedStatement ps = null;

        for (LocalDate date : seat.getBlockOutDates()) {
            Date newDate = Date.valueOf(date);

            String queryString = "INSERT OR IGNORE INTO BlockOut(seatNo, date) VALUES (?,?)";
            ps = connection.prepareStatement(queryString);
            ps.setInt( TableValues.SEATNO.ordinal() + 1, seat.getSeatNo());

            // TODO
            ps.setDate(TableValues.DATE.ordinal() + 1, newDate);
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
            // TODO
            Date date = rs.getDate(1);
            dates.add(date.toLocalDate());
        }

        rs.close();
        ps.close();

        return dates;
    }
}
