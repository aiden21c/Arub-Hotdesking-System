package main.model.dao.user;

import main.Main;
import main.model.dao.AbstractDAO;
import main.model.object.seat.Seat;
import main.model.object.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WhiteListDAO extends AbstractDAO {
    private enum TableValues {USERNAME, SEATNO}

    protected void addWhiteList(User user) throws SQLException {
        assert connection != null;
        PreparedStatement ps = null;

        for (Seat seat : user.getWhiteList()) {
            String queryString = "INSERT OR IGNORE INTO WhiteList(username, seatNo) VALUES (?,?)";
            ps = connection.prepareStatement(queryString);
            ps.setString(TableValues.USERNAME.ordinal() + 1, user.getUsername());
            ps.setInt(TableValues.SEATNO.ordinal() + 1, seat.getSeatNo());
            ps.execute();
        }

        ps.close();
    }

    protected ArrayList<Seat> getWhiteList(String username) throws SQLException, ClassNotFoundException {
        assert connection != null;
        ArrayList<Seat> whitelist = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        String queryString = "select seatNo from WhiteList where username = ?";
        ps = connection.prepareStatement(queryString);
        ps.setString(TableValues.USERNAME.ordinal() + 1, username);

        rs = ps.executeQuery();

        while (rs.next()) {
            int seatNo = rs.getInt(TableValues.SEATNO.ordinal());
            whitelist.add(Main.seatDAO.createSeat(seatNo));
        }

        ps.close();
        rs.close();

        return whitelist;
    }
}
