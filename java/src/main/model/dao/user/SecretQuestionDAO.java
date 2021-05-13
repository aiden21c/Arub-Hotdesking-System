package main.model.dao.user;

import main.model.dao.AbstractDAO;
import main.model.object.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecretQuestionDAO extends AbstractDAO {

    private enum TableValues {USERNAME, QUESTION, ANSWER}

    protected void addSecretQuestion(User user) throws SQLException {
        assert connection != null;
        String queryString = "INSERT OR IGNORE INTO SecretQuestion(username, question, answer) VALUES (?,?,?)";

        PreparedStatement ps = connection.prepareStatement(queryString);

        ps.setString(TableValues.USERNAME.ordinal() + 1, user.getUsername());
        ps.setString(TableValues.QUESTION.ordinal() + 1, user.getQuestion());
        ps.setString(TableValues.ANSWER.ordinal() + 1, user.getAnswer());

        ps.execute();
        ps.close();
    }

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
}
