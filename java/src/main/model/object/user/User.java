package main.model.object.user;

import main.Main;
import main.model.object.seat.Seat;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class User {
    public enum SecretQuestion {QUESTION, ANSWER}

    protected int empID;
    protected String firstName;
    protected String lastName;
    protected String role;
    protected int age;
    protected String username;
    protected String password;
    protected String[] secretQuestion;
    protected ArrayList<Seat> whitelist;

    /**
     * Constructor to be used when creating a user that has already existed in the system
     * @param empID the id of the user
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param role the role of the user
     * @param age the user's age
     * @param username the username of the user. Must be unique for all users
     * @param password the password for the user
     * @param secretQuestion the secret question array of the user. Features both question and answer
     * @param whitelist the whitelist of the seats the user is able to book in their next booking
     */
    protected User(int empID, String firstName, String lastName, String role, int age,
                String username, String password, String[] secretQuestion, ArrayList<Seat> whitelist) {
        this.empID = empID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.age = age;
        this.username = username;
        this.password = password;
        this.secretQuestion = secretQuestion;
        this.whitelist = whitelist;
    }

    /**
     * Constructor to be used when creating a new user from the UI
     * @param empID the id of the user
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param role the role of the user
     * @param age the user's age
     * @param username the username of the user. Must be unique for all users
     * @param password the password for the user
     * @param secretQuestion the secret question array of the user. Features both question and answer
     * @throws SQLException
     */
    protected User(int empID, String firstName, String lastName, String role, int age,
                   String username, String password, String[] secretQuestion) throws SQLException {
        this.empID = empID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.age = age;
        this.username = username;
        this.password = password;
        this.secretQuestion = secretQuestion;
        this.whitelist = Main.seatDAO.getAllSeats();
    }


    public int getEmpID() {return empID;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

    public int getAge(){return age;}
    public void setAge(int age) {this.age = age;}

    public String getUsername() {return username;}

    public String getPassword() {return password;}
    public void setNewPassword(String password) {this.password = password;}

    public String[] getSecretQuestion() {return secretQuestion;}
    public void setSecretQuestion(String[] sq) {
        secretQuestion[SecretQuestion.QUESTION.ordinal()] = sq[SecretQuestion.QUESTION.ordinal()];
        secretQuestion[SecretQuestion.ANSWER.ordinal()] = sq[SecretQuestion.ANSWER.ordinal()];
    }

    public ArrayList<Seat> getWhiteList() {return whitelist;}
    public void setWhitelist(ArrayList<Seat> whitelist) {
        this.whitelist = whitelist;
    }

    /**
     * Takes in a user, and checks if the values of the user given are equal to it's own instance values
     * @param user the user object to be compared against
     * @return boolean identifying whether the given user object has equal values
     */
    public boolean equals(User user) {
        boolean equals = false;
        int count = 0;

        if (empID == user.empID) {
            if (firstName.equals(user.firstName)) {
                if (lastName.equals(user.lastName)) {
                    if (role.equals(user.role)) {
                        if (age == user.age) {
                            if (username.equals(user.username)) {
                                if (password.equals((user.password))) {
                                    if(secretQuestionEquals(user.getSecretQuestion())) {
                                        for (int i = 0; i < whitelist.size(); i++) {
                                            if (whitelist.get(i).equals(user.whitelist.get(i))) {
                                                count++;
                                            }
                                        }
                                        if (count == whitelist.size()) {
                                            equals = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return equals;
    }

    /**
     * Update the whitelist, resetting it to default and then removing the given seat
     * @param seat the most recently booked seat to remove from the whitelist
     */
    public void updateWhiteList(Seat seat) throws SQLException {
        this.whitelist = Main.seatDAO.getAllSeats();

        for (int i = 0; i < whitelist.size(); i++) {
            if(whitelist.get(i).getSeatNo() == seat.getSeatNo()) {
                whitelist.remove(i);
            }
        }
    }

    /**
     * Checks if the secretQuestion array given is equal to the instance variable
     * @param sq the secret question array given
     * @return
     */
    private boolean secretQuestionEquals(String[] sq) {
        boolean eq = false;
        if(secretQuestion[SecretQuestion.QUESTION.ordinal()].equals(sq[SecretQuestion.QUESTION.ordinal()])) {
            if(secretQuestion[SecretQuestion.ANSWER.ordinal()].equals(sq[SecretQuestion.ANSWER.ordinal()])) {
                eq = true;
            }
        }
        return eq;
    }

}
