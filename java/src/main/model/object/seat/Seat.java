package main.model.object.seat;

import java.time.LocalDate;
import java.util.ArrayList;

public class Seat {
    private int seatNo;
    private boolean blockout;
    private ArrayList<LocalDate> blockDates;

    public Seat(int seatNo, boolean blockout, ArrayList<LocalDate> blockDates) {
        this.seatNo = seatNo;
        this.blockout = blockout;
        this.blockDates = blockDates;
    }

    public int getSeatNo() {return seatNo;}

    public ArrayList<LocalDate> getBlockOutDates() {return blockDates;}

    public boolean getBlockOut() {return blockout;}

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
}
