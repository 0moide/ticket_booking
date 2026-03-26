package com.example.mywebsite.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Booking {
    private int quantitySeats;
    private int rows;
    private int seatsPerRow;
    @Id
    private long id;

    @OneToOne
    @JoinColumn(name = "session_id")
    @JsonIgnore
    private Session session;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
        name = "booking_seats_map",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private java.util.List<Seat> seats = new java.util.ArrayList<>();

    protected Booking() {}

    public Booking(long id, int quantityRow, int quantityNumber) {
        this.id = id;
        this.rows = quantityRow;
        this.seatsPerRow = quantityNumber;
        this.quantitySeats = quantityRow * quantityNumber;
        this.seats = new ArrayList<>(this.quantitySeats);
        
        for (int i = 0; i < this.quantitySeats; ++i) {
            Seat seat = new Seat(i / quantityNumber + 1, i % quantityNumber + 1);
            seat.setBooking(this);
            this.seats.add(seat);
        }
    }
    
    public List<Seat> getSeats() {
        return seats;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public int getQuantitySeats() { return quantitySeats; }

    public long getId() { return id; }

    
    @Transactional
    public boolean reserveSeat(int seatId, String userName) {
        if (seatId >= 0 && seatId < seats.size()) {
            Seat seat = seats.get(seatId);
            if (seat.getStatus() == SeatStatus.Available) {
                seat.setStatus(SeatStatus.Reserved);
                seat.setName(userName);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean unreserveSeat(int seatId, String userKey){
        int digitalKey = 0;
        try{
            digitalKey = Integer.parseInt(userKey);
        }
        catch (Exception e){
            return false;
        }

        if (seatId >= 0 && seatId < seats.size()) {
            Seat seat = seats.get(seatId);
            if (seat.getStatus() == SeatStatus.Reserved){
                int key = seat.getKey();
                if(digitalKey == key){
                    seat.setStatus(SeatStatus.Available);
                    return true;
                }
            }
        }
        return false;
    }

    public void setSession(Session session) {this.session = session; }
    
}