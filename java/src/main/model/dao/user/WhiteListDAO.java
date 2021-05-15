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

    /**
     *
     * @param user
     * @throws SQLException
     */
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

    /**
     *
     * @param username
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    protected ArrayList<Seat> getWhiteList(String username) throws SQLException, ClassNotFoundException {
        assert connection != null;
        ArrayList<Seat> whitelist = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        String queryString = "select seatNo from WhiteList where username = ?";
        ps = connection.prepareStatement(queryString);
        ps.setString(TableValues.USERNAME.ordinal() + 1, username);

        rs = ps.executeQuery();

        ArrayList<Integer> seatNo = new ArrayList<>();

        while (rs.next()) {
            seatNo.add(rs.getInt(TableValues.SEATNO.ordinal()));
        }

        rs.close();
        ps.close();

        for (int i = 0; i < seatNo.size(); i++) {
            whitelist.add(Main.seatDAO.createSeat(seatNo.get(i)));
        }

        return whitelist;
    }

    /**
     *
     * @param username
     * @param seatNo
     * @throws SQLException
     */
    protected void deleteWhiteList(String username, int seatNo) throws SQLException {
        assert connection != null;

        String queryString = "delete from WhiteList where username = ? and seatNo = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setString(TableValues.USERNAME.ordinal() + 1, username);
        ps.setInt(TableValues.SEATNO.ordinal() + 1, seatNo);

        ps.execute();
        ps.close();
    }

    /**
     *
     * @param username
     * @throws SQLException
     */
    protected void deleteWhiteList(String username) throws SQLException {
        assert connection != null;

        String queryString = "delete from WhiteList where username = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setString(TableValues.USERNAME.ordinal() + 1, username);

        ps.execute();
        ps.close();
    }
}
