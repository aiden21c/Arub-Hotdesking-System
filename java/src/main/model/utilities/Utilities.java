package main.model.utilities;

import java.security.SecureRandom;
import java.time.LocalDate;

import static java.lang.Integer.parseInt;

public class Utilities {

    /**
     * Accepts string in format "YYYY-MM-DD" and converts it to date object
     * @param dateString String in format "YYYY-MM-DD"
     * @return Date object of given string
     */
    public static LocalDate stringToDate(String dateString) {
        String[] tokens = dateString.split("-");
        LocalDate date = LocalDate.of(parseInt(tokens[0]), parseInt(tokens[1]), parseInt(tokens[2]));
        return date;
    }

    /**
     * Creates a new password from the characters defined in the string 'chars'
     * @return a new 10 character password
     */
    public static String generateNewPassword() {
        // Characters to create te new password from
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        // The length of the new password
        int length = 10;

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++)
        {
            int rand = random.nextInt(chars.length());
            sb.append(chars.charAt(rand));
        }

        return sb.toString();
    }
}
