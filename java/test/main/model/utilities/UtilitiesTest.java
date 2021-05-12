package main.model.utilities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;


class UtilitiesTest {

    @Test
    @DisplayName("Test date should accurately map to date object")
    public void testStringToDate() {
        String testDate = "2021-05-12";
        LocalDate testConverted = Utilities.stringToDate(testDate);
        LocalDate date = LocalDate.of(2021, 5, 12);
        assertEquals(date, testConverted, "Should be equal if date conversion is correct");
    }


}