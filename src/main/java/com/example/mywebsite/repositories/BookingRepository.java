package com.example.mywebsite.repositories;

import com.example.mywebsite.entities.Seat;
import com.example.mywebsite.entities.Booking;

import jakarta.persistence.LockModeType;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findBySession_Id(long sessionId);

     @Query("SELECT b FROM Booking b JOIN b.seats s " +
           "WHERE b.session.id = :sessionId " +
           "AND s.row = :row " +
           "AND s.number = :number")
    Optional<Booking> findBookingBySessionAndSeat(
        @Param("sessionId") Long sessionId, 
        @Param("row") int row, 
        @Param("number") int number
    );
    
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT s FROM Seat s WHERE s.id = :id")
    Optional<Seat> findSeatByIdWithOptimisticLock(@Param("id") Long id);
}