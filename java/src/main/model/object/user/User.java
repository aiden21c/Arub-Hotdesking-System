package main.model.object.user;

import main.model.object.seat.Seat;

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

    public int getEmpID() {return empID;}

    public String getFirstName() {return firstName;}

    public String getLastName() {return lastName;}

    public String getRole() {return role;}

    public int getAge(){return age;}

    public String getUsername() {return username;}

    public String getPassword() {return password;}

    public String getQuestion() {return secretQuestion[SecretQuestion.QUESTION.ordinal()];}

    public String getAnswer() {return secretQuestion[SecretQuestion.ANSWER.ordinal()];}

    public ArrayList<Seat> getWhiteList() {return whitelist;}

    public boolean equals(User user) {
        boolean equals = false;
        int count = 0;

        if (empID == user.empID) {
            if (firstName.equals(user.firstName)) {
                if (lastName.equals(user.lastName)) {
                    if (role.equals(user.role)) {
                        if (age == age) {
                            if (username.equals(user.username)) {
                                if (password.equals((user.password))) {
                                    if (getQuestion().equals(user.getQuestion())) {
                                        if (getAnswer().equals(user.getAnswer())) {
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
        }

        return equals;
    }

}
