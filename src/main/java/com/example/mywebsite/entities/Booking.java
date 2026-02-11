package com.example.mywebsite.entities;

import java.util.ArrayList;

public class Booking {
    public class BookedSeat {
        private int row;
        private int number;
        private SeatStatus status;
        private String name = null;
        private int id;

        public BookedSeat(int row, int number, int id) {
            this.row = row;
            this.number = number;
            this.status = SeatStatus.Available;
            this.id = id;
        }

        public int getRow() { return row; }
        public void setRow(int row) { this.row = row; }

        public int getNumber() { return number; }
        public void setNumber(int number) { this.number = number; }

        public SeatStatus getStatus() { return status; }
        public void setStatus(SeatStatus status) { this.status = status; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
    }

    private ArrayList<BookedSeat> bookedSeats;
    private int quantitySeats;
    private int rows;
    private int seatsPerRow;

    public Booking(int quantityRow, int quantityNumber) {
        this.rows = quantityRow;
        this.seatsPerRow = quantityNumber;
        this.quantitySeats = quantityRow * quantityNumber;
        this.bookedSeats = new ArrayList<>(this.quantitySeats);
        
        for (int i = 0; i < this.quantitySeats; ++i) {
            this.bookedSeats.add(new BookedSeat(i / quantityNumber + 1, 
                                               i % quantityNumber + 1, i));
        }
    }
    
    public ArrayList<BookedSeat> getBookedSeats() {
        return bookedSeats;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getSeatsPerRow() {
        return seatsPerRow;
    }
    
    public boolean reserveSeat(int seatId, String userName) {
        if (seatId >= 0 && seatId < bookedSeats.size()) {
            BookedSeat seat = bookedSeats.get(seatId);
            if (seat.getStatus() == SeatStatus.Available) {
                seat.setStatus(SeatStatus.Reserved);
                seat.setName(userName);
                return true;
            }
        }
        return false;
    }
}