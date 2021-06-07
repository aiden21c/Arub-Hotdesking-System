package main.model.dao.user;

import main.Main;
import main.model.dao.AbstractDAO;
import main.model.object.seat.Seat;
import main.model.object.user.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WhiteListDAO extends AbstractDAO {
    private enum TableValues {USERNAME, SEATNO}

    /**
     * Adds the white list array of a given user to the database
     * @param user the user whos whitelist is to be added
     * @throws SQLException if no user currently exists in the database with given username
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

        assert ps != null;
        ps.close();
    }

    /**
     * Obtain a whitelist from the database for a given username
     * @param username the username who's whitelist is to be obtained
     * @return a seat array containing all the seats whitelisted for given user
     * @throws SQLException if there are no entries in the whitelist for the given user
     */
    protected ArrayList<Seat> getWhiteList(String username) throws SQLException {
        assert connection != null;
        ArrayList<Seat> whitelist = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        String queryString = "select seatNo from WhiteList where username = ? order by seatNo asc";
        ps = connection.prepareStatement(queryString);
        ps.setString(TableValues.USERNAME.ordinal() + 1, username);

        rs = ps.executeQuery();

        ArrayList<Integer> seatNo = new ArrayList<>();

        while (rs.next()) {
            seatNo.add(rs.getInt(TableValues.SEATNO.ordinal()));
        }

        rs.close();
        ps.close();

        for (Integer integer : seatNo) {
            whitelist.add(Main.seatDAO.createSeat(integer));
        }

        return whitelist;
    }

    /**
     * Deletes all whitelist entries for a given username
     * @param username the user who's whitelist is to be deleted
     * @throws SQLException if a user cannot be found with this username
     */
    protected void deleteWhiteList(String username) throws SQLException {
        assert connection != null;

        String queryString = "delete from WhiteList where username = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setString(TableValues.USERNAME.ordinal() + 1, username);

        ps.execute();
        ps.close();
    }

    /**
     * Exports all the whitelist entries into a CSV file
     * @throws SQLException
     * @throws IOException
     */
    public void export() throws SQLException, IOException {
        super.export("WhiteList ORDER BY username, seatNo");
    }
}
