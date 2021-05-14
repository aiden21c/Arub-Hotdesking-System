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

    public void addSeat(Seat seat) throws SQLException, ClassNotFoundException {
        assert connection != null;
        String queryString = "INSERT OR IGNORE INTO Seat(seatNo, blockOut) VALUES (?,?)";

        PreparedStatement ps = connection.prepareStatement(queryString);

        ps.setInt(TableValues.SEATNO.ordinal() + 1, seat.getSeatNo());
        ps.setBoolean(TableValues.BLOCKOUT.ordinal() + 1, seat.getBlockOut());

        ps.execute();
        ps.close();

        Main.blockOutDAO.addDates(seat);
    }

    public Seat createSeat(int seatNo) throws SQLException, ClassNotFoundException {
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

        return new Seat(seatNo, blockOut, getBlockOut(seatNo));
    }

    private ArrayList<LocalDate> getBlockOut(int seatNo) throws SQLException, ClassNotFoundException {
        return Main.blockOutDAO.createDates(seatNo);
    }
}
