package main.model.utilities;

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
}
