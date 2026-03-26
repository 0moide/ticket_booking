package com.example.mywebsite.dto;

import java.time.LocalDateTime;

public class SessionDto {
    private Long id;
    private LocalDateTime time;
    private int hallNumber;
    private int availableSeats;

    public SessionDto() {}

    public SessionDto(Long id, LocalDateTime time, int hallNumber, int availableSeats) {
        this.id = id;
        this.time = time;
        this.hallNumber = hallNumber;
        this.availableSeats = availableSeats;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getTime() { return time; }
    public void setTime(LocalDateTime time) { this.time = time; }

    public int getHallNumber() { return hallNumber; }
    public void setHallNumber(int hallNumber) { this.hallNumber = hallNumber; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}