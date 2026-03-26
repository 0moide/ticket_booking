package com.example.mywebsite.controllers;

import com.example.mywebsite.entities.Booking;
import com.example.mywebsite.entities.Film;
import com.example.mywebsite.entities.Seat;
import com.example.mywebsite.entities.SeatStatus;
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
@CrossOrigin(origins = "http://localhost:3000")
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
    
    @GetMapping("/films/{filmId}/sessions/{sessionId}/seats")
    public Map<String, Object> getSeats(@PathVariable Long filmId, @PathVariable Long sessionId) {
        Session session = databaseService.getSessionById(sessionId);
        Booking booking = session.getBooking();
        
        Map<String, Object> response = new HashMap<>();
        response.put("hallNumber", session.getHallNumber());
        response.put("seats", booking.getSeats());
        response.put("rows", booking.getRows());
        response.put("seatsPerRow", booking.getSeatsPerRow());
        
        return response;
    }
    
    @PostMapping("/films/{filmId}/sessions/{sessionId}/seats/{seatNumber}/reserve")
    public ResponseEntity<?> reserveSeat(@PathVariable Long filmId,
                                        @PathVariable Long sessionId,
                                        @PathVariable int seatNumber,
                                        @RequestBody Map<String, String> request) {
        String userName = request.get("userName");
        String userEmail = request.get("userEmail");
        System.out.println("номер места = " + seatNumber);
        System.out.println("email = " + userEmail);
        
        int key = databaseService.reserveSeat(filmId, sessionId, seatNumber, userName, userEmail);
        if(key != 0){
            String filmTitle = databaseService.getFilmById(filmId).getTitle();
            String sessionTime = databaseService.getSessionTime(sessionId);
            int hallNumber = databaseService.getHallNumber(sessionId);
            String seatsInfo = String.format("%d ряд, %d место", 
                                            databaseService.getSeatRow(sessionId, seatNumber), 
                                            databaseService.getSeatNumber(sessionId, seatNumber));
            emailService.sendBookingConfirmation(userEmail, userName, filmTitle, sessionTime, hallNumber, seatsInfo, String.valueOf(key));
            return ResponseEntity.ok(Map.of("success", true));
        }
        
        return ResponseEntity.ok(Map.of("success", false));
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/films/{filmId}/sessions/{sessionId}/seats/reserve-multiple")
    public ResponseEntity<?> reserveMultipleSeats(@PathVariable Long filmId,
                                                @PathVariable Long sessionId,
                                                @RequestBody Map<String, Object> request) {
        String userName = (String) request.get("userName");
        String userEmail = (String) request.get("userEmail");
        List<Integer> seatNumbers = (List<Integer>) request.get("seatNumbers");
        
        System.out.println("Бронирование нескольких мест: " + seatNumbers);
        System.out.println("email = " + userEmail);
        
        int key = databaseService.reserveMultipleSeats(
            filmId, sessionId, seatNumbers, userName, userEmail);
        
        if(key != 0){
            List<String> seatsInfo = new ArrayList<>();
            String filmTitle = databaseService.getFilmById(filmId).getTitle();
            String sessionTime = databaseService.getSessionTime(sessionId);
            int hallNumber = databaseService.getHallNumber(sessionId);
            for(int seatNumber : seatNumbers){
                seatsInfo.add(String.format("%d ряд, %d место", 
                                                databaseService.getSeatRow(sessionId, seatNumber), 
                                                databaseService.getSeatNumber(sessionId, seatNumber)));
            }
            String allSeatsInfo = String.join(" • ", seatsInfo);
            emailService.sendBookingConfirmation(userEmail, userName, filmTitle, sessionTime, hallNumber, allSeatsInfo, String.valueOf(key));
            return ResponseEntity.ok(Map.of("success", true));
        }
        
        return ResponseEntity.ok(Map.of("success", false));
    }

    @PostMapping("/films/{filmId}/sessions/{sessionId}/seats/unreserve-multiple")
    public ResponseEntity<?> unreserveMultipleSeats(@PathVariable Long sessionId,
                                                @RequestBody Map<String, Object> request) {
        String key = (String) request.get("key");

        System.out.println("Отмена бронирования");

        String email = databaseService.unreserveMultipleSeats(sessionId, key);
        System.out.println("Почта для отмены:");
        System.out.println(email);
        if(!email.isEmpty()){
            emailService.sendBookingCancelReservation(email);
            return ResponseEntity.ok(Map.of("success", true));
        }
        return ResponseEntity.ok(Map.of("success", false));
    }

    @GetMapping("/films/with-sessions")
    public List<Map<String, Object>> getAllFilmsWithSessions() {
        List<Film> films = databaseService.getAllFilms();
        List<Map<String, Object>> result = new ArrayList<>();
        
        for (Film film : films) {
            Map<String, Object> filmData = new HashMap<>();
            filmData.put("id", film.getId());
            filmData.put("title", film.getTitle());
            filmData.put("genre", film.getGenre());
            filmData.put("duration", film.getDuration());
            filmData.put("minAge", film.getMinAge());
            filmData.put("description", film.getDescription());
            filmData.put("posterUrl", film.getPosterUrl());
            
            // Добавляем сеансы
            List<Map<String, Object>> sessionsData = new ArrayList<>();
            List<Session> sessions = film.getSessions();
            for (int i = 0; i < sessions.size(); i++) {
                Session session = sessions.get(i);
                Map<String, Object> sessionData = new HashMap<>();
                sessionData.put("id", session.getId());
                sessionData.put("index", i);
                sessionData.put("time", session.getTime().toString());
                sessionData.put("hallNumber", session.getHallNumber());
                
                // Подсчитываем свободные места
                Booking booking = session.getBooking();
                int availableSeats = 0;
                if (booking != null && booking.getSeats() != null) {
                    List<Seat> seats = booking.getSeats();
                    for (Seat seat : seats) {
                        // Используем enum для сравнения
                        if (seat.getStatus() == SeatStatus.Available) {
                            availableSeats++;
                        }
                    }
                }
                sessionData.put("availableSeats", availableSeats);
                sessionsData.add(sessionData);
            }
            filmData.put("sessions", sessionsData);
            result.add(filmData);
        }
        
        return result;
    }

    @GetMapping("/sessions/{sessionId}/seats")
    public Map<String, Object> getSeatsBySessionId(@PathVariable Long sessionId) {
        Session session = databaseService.getSessionById(sessionId);
        if (session == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Session not found");
            return error;
        }
        
        Booking booking = session.getBooking();
        
        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getId());
        response.put("filmId", session.getFilm().getId());
        response.put("filmTitle", session.getFilm().getTitle());
        response.put("hallNumber", session.getHallNumber());
        response.put("sessionTime", session.getTime().toString());
        response.put("seats", booking.getSeats());
        response.put("rows", booking.getRows());
        response.put("seatsPerRow", booking.getSeatsPerRow());
        
        return response;
    }
}