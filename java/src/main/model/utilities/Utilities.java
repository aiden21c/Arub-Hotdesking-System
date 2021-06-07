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

    /**
     * Takes in a string and checks whether it is a valid int
     * @param string the string to check integer status
     * @return
     */
    public static int isInt(String string) {
        int x = 0;
        if (string.matches("-?(0|[1-9]\\d*)")) {
            x = Integer.parseInt(string);
        }
        return x;
    }

    /**
     * Inspired from <https://www.codejava.net/coding/java-code-example-to-export-from-database-to-csv-file>
     * Escape with Double Quotes all forward slashes within a string
     * @param value the string to double quotes escape the \
     * @return a new string with forward slashes escaped
     */
    public static String escapeDoubleQuotes(String value) {
        return value.replaceAll("\"", "\"\"");
    }
}
