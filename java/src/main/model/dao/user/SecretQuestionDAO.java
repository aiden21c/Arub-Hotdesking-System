package main.model.dao.user;

import main.model.dao.AbstractDAO;
import main.model.object.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecretQuestionDAO extends AbstractDAO {

    private enum TableValues {USERNAME, QUESTION, ANSWER}

    /**
     * Adds a secret question into the database for a given username
     *      If there is already an entry in the table for this username, the method identifies this
     *      as an "update values" and removes the current entry from the database before adding the new one
     * @param user the user who's secret question this is
     * @param exists variable to identify whether there is already an entry with the given username
     * @throws SQLException
     */
    protected void addSecretQuestion(User user, boolean exists) throws SQLException {
        if (exists) {
            deleteSecretQuestion(user.getUsername());
        }

        assert connection != null;
        String queryString = "INSERT INTO SecretQuestion(username, question, answer) VALUES (?,?,?)";

        PreparedStatement ps = connection.prepareStatement(queryString);

        ps.setString(TableValues.USERNAME.ordinal() + 1, user.getUsername());
        ps.setString(TableValues.QUESTION.ordinal() + 1, user.getQuestion());
        ps.setString(TableValues.ANSWER.ordinal() + 1, user.getAnswer());

        ps.execute();
        ps.close();
    }

    /**
     * Gets a string array of the secret question and answer for a given user
     * @param username the username whos secret question to retrieve
     * @return a string array containing the secret question and it's answer
     * @throws SQLException if there is no entries in the table for the given username
     */
    protected String[] getTable(String username) throws SQLException {
        String[] secretQuestion = new String[2];
        PreparedStatement ps;
        ResultSet rs;
        String queryString = "select question, answer from SecretQuestion where username = ?";
        ps = connection.prepareStatement(queryString);
        ps.setString(TableValues.USERNAME.ordinal() + 1, username);
        rs = ps.executeQuery();

        secretQuestion[User.SecretQuestion.QUESTION.ordinal()] = rs.getString("question");
        secretQuestion[User.SecretQuestion.ANSWER.ordinal()] = rs.getString("answer");

        ps.close();
        rs.close();

        return secretQuestion;
    }

    /**
     * Deletes a secret question entry from the database
     * @param username the username of the secret question entry to delete
     * @throws SQLException if an entry for the given username cannot be found
     */
    public void deleteSecretQuestion(String username) throws SQLException {
        assert connection != null;
        String queryString = "delete from SecretQuestion where username = ?";
        PreparedStatement ps = connection.prepareStatement(queryString);
        ps.setString(1, username);

        ps.execute();
        ps.close();

    }
}
