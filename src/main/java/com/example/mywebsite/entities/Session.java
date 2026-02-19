package com.example.mywebsite.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Session {
    @Id
    @Column(name = "session_id")

    private long id;
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "film_id") 
    @JsonIgnore
    private Film film; 

    @OneToOne(mappedBy = "session", cascade = CascadeType.ALL)
    private Booking booking;

    private int hallNumber;

    protected Session() {}

    public Session(long id, LocalDateTime time, Booking booking, int hallNumber){
        this.id = id;
        this.time = time;
        this.booking = booking;
        this.hallNumber = hallNumber;
    }

    public LocalDateTime getTime() { return time; }
    public void setTime(LocalDateTime time) { this.time = time; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public int getHallNumber() { return hallNumber; }
    public void setHallNumber(int hallNumber) {this.hallNumber = hallNumber; }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }

}
