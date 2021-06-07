package main.model.object.seat;

import main.Main;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Seat {
    private int seatNo;
    private boolean blockout;
    private ArrayList<LocalDate> blockDates;

    public enum Available {LOCKED, OPEN, BOOKED}

    public Seat(int seatNo, boolean blockout, ArrayList<LocalDate> blockDates) {
        this.seatNo = seatNo;
        this.blockout = blockout;
        this.blockDates = blockDates;
    }

    public int getSeatNo() {return seatNo;}

    public ArrayList<LocalDate> getBlockOutDates() {return blockDates;}

    public boolean getBlockOut() {return blockout;}

    /**
     * Takes in a seat, and checks if the values of the seat given are equal to it's own instance values
     * @param s the seat object to be compared against
     * @return boolean identifying whether the given seat object has equal values
     */
    public boolean equals(Seat s) {
        boolean equals = false;
        int size = blockDates.size();
        int dateCount = 0;
        if (seatNo == s.seatNo) {
            if (blockout == s.blockout) {
                if (size == s.blockDates.size()) {
                    for (int i = 0; i < size; i++) {
                        if (blockDates.get(i).equals(s.blockDates.get(i))) {
                            dateCount++;
                        }
                    }
                    if (dateCount == size) {
                        equals = true;
                    }
                }
            }
        }

        return equals;
    }

    /**
     * Check the availability of a seat on a certain date
     * @param date the date to check availability for
     * @return the current availability status of the seat
     */
    public Available isLockedOnDate(LocalDate date) {
        Available status = Available.OPEN;

        if (blockDates.contains(date) || blockout) {
                status = Available.LOCKED;
        }

        try {
            if (Main.bookingDAO.isBooked(seatNo, date)) {
                status = Available.BOOKED;
            }
        } catch (SQLException ignored) { }

        return status;
    }
}
