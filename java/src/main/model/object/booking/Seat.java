package main.model.object.booking;

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
}
