package com.example.mywebsite.entities;

import java.time.LocalDateTime;

public class Session {
    private LocalDateTime time;
    private Booking booking;
    private int hallNumber;

    public Session(LocalDateTime time, Booking booking, int hallNumber){
        this.time = time;
        this.booking = booking;
        this.hallNumber = hallNumber;
    }

    public LocalDateTime getTime() { return time; }

    public Booking getBooking() { return booking; }

    public int getHallNumber() { return hallNumber; }

}
