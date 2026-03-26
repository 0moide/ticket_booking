package com.example.mywebsite.controllers;

import com.example.mywebsite.entities.Booking;
import com.example.mywebsite.entities.Film;
import com.example.mywebsite.entities.Session;
import com.example.mywebsite.services.DatabaseService;
import com.example.mywebsite.services.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@RestController
@EnableAsync
@RequestMapping("/api")
public class ApiController {

    private final DatabaseService databaseService;
    @Autowired
    private EmailService emailService;
    
    public ApiController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Bean(name = "emailExecutor")
    public Executor emailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("email-");
        executor.initialize();
        return executor;
    }
    
    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return databaseService.getAllFilms();
    }
    
    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable Long id) {
        return databaseService.getFilmById(id);
    }
    
    @GetMapping("/films/{filmId}/sessions/{sessionIndex}/seats")
    public Map<String, Object> getSeats(@PathVariable Long filmId, @PathVariable int sessionIndex) {
        Film film = databaseService.getFilmById(filmId);
        Session session = film.getSessions().get(sessionIndex);
        Booking booking = session.getBooking();
        
        Map<String, Object> response = new HashMap<>();
        response.put("hallNumber", session.getHallNumber());
        response.put("seats", booking.getSeats());
        response.put("rows", booking.getRows());
        response.put("seatsPerRow", booking.getSeatsPerRow());
        
        return response;
    }
    
    @PostMapping("/films/{filmId}/sessions/{sessionIndex}/seats/{seatNumber}/reserve")
    public ResponseEntity<?> reserveSeat(@PathVariable Long filmId,
                                        @PathVariable int sessionIndex,
                                        @PathVariable int seatNumber,
                                        @RequestBody Map<String, String> request) {
        String userName = request.get("userName");
        String userEmail = request.get("userEmail");
        System.out.println("номер места = " + seatNumber);
        System.out.println("email = " + userEmail);
        
        int key = databaseService.reserveSeat(filmId, sessionIndex, seatNumber, userName, userEmail);
        if(key != 0){
            String filmTitle = databaseService.getFilmById(filmId).getTitle();
            String sessionTime = databaseService.getSessionTime(filmId, sessionIndex);
            int hallNumber = databaseService.getHallNumber(filmId, sessionIndex);
            String seatsInfo = String.format("%d ряд, %d место", 
                                            databaseService.getSeatRow(filmId, sessionIndex, seatNumber), 
                                            databaseService.getSeatNumber(filmId, sessionIndex, seatNumber));
            emailService.sendBookingConfirmation(userEmail, userName, filmTitle, sessionTime, hallNumber, seatsInfo, String.valueOf(key));
            return ResponseEntity.ok(Map.of("success", true));
        }
        
        return ResponseEntity.ok(Map.of("success", false));
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/films/{filmId}/sessions/{sessionIndex}/seats/reserve-multiple")
    public ResponseEntity<?> reserveMultipleSeats(@PathVariable Long filmId,
                                                @PathVariable int sessionIndex,
                                                @RequestBody Map<String, Object> request) {
        String userName = (String) request.get("userName");
        String userEmail = (String) request.get("userEmail");
        List<Integer> seatNumbers = (List<Integer>) request.get("seatNumbers");
        
        System.out.println("Бронирование нескольких мест: " + seatNumbers);
        System.out.println("email = " + userEmail);
        
        int key = databaseService.reserveMultipleSeats(
            filmId, sessionIndex, seatNumbers, userName, userEmail);
        
        if(key != 0){
            List<String> seatsInfo = new ArrayList<>();
            String filmTitle = databaseService.getFilmById(filmId).getTitle();
            String sessionTime = databaseService.getSessionTime(filmId, sessionIndex);
            int hallNumber = databaseService.getHallNumber(filmId, sessionIndex);
            for(int seatNumber : seatNumbers){
                seatsInfo.add(String.format("%d ряд, %d место", 
                                                databaseService.getSeatRow(filmId, sessionIndex, seatNumber), 
                                                databaseService.getSeatNumber(filmId, sessionIndex, seatNumber)));
            }
            String allSeatsInfo = String.join(" • ", seatsInfo);
            emailService.sendBookingConfirmation(userEmail, userName, filmTitle, sessionTime, hallNumber, allSeatsInfo, String.valueOf(key));
            return ResponseEntity.ok(Map.of("success", true));
        }
        
        return ResponseEntity.ok(Map.of("success", false));
    }

    @PostMapping("/films/{filmId}/sessions/{sessionIndex}/seats/unreserve-multiple")
    public ResponseEntity<?> unreserveMultipleSeats(@PathVariable Long filmId,
                                                @PathVariable int sessionIndex,
                                                @RequestBody Map<String, Object> request) {
        String key = (String) request.get("key");

        System.out.println("Отмена бронирования");

        String email = databaseService.unreserveMultipleSeats(filmId, sessionIndex, key);
        System.out.println("Почта для отмены:");
        System.out.println(email);
        if(!email.isEmpty()){
            emailService.sendBookingCancelReservation(email);
            return ResponseEntity.ok(Map.of("success", true));
        }
        return ResponseEntity.ok(Map.of("success", false));
    }
}