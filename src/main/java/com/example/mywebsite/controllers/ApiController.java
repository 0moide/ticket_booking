package com.example.mywebsite.controllers;

import com.example.mywebsite.entities.Booking;
import com.example.mywebsite.entities.Film;
import com.example.mywebsite.entities.Session;
import com.example.mywebsite.services.DatabaseService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    // private final FilmService filmService;

    private final DatabaseService databaseService;
    
    public ApiController(DatabaseService databaseService) {
        // this.filmService = filmService;
        this.databaseService = databaseService;
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
                                        @PathVariable int seatNumber,  // теперь seatNumber
                                        @RequestBody Map<String, String> request) {
        String userName = request.get("userName");
        System.out.println("номер места = " + seatNumber);
        boolean success = databaseService.reserveSeat(filmId, sessionIndex, seatNumber, userName);
        
        return ResponseEntity.ok(Map.of("success", success));
    }
}