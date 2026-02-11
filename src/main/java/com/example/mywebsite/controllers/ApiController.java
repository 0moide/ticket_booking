package com.example.mywebsite.controllers;

import com.example.mywebsite.entities.Booking;
import com.example.mywebsite.entities.Film;
import com.example.mywebsite.entities.Session;
import com.example.mywebsite.services.FilmService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    private final FilmService filmService;
    
    public ApiController(FilmService filmService) {
        this.filmService = filmService;
    }
    
    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }
    
    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable Long id) {
        return filmService.getFilmById(id);
    }
    
    @GetMapping("/films/{filmId}/sessions/{sessionIndex}/seats")
    public Map<String, Object> getSeats(@PathVariable Long filmId, @PathVariable int sessionIndex) {
        Film film = filmService.getFilmById(filmId);
        Session session = film.getSessions().get(sessionIndex);
        Booking booking = session.getBooking();
        
        Map<String, Object> response = new HashMap<>();
        response.put("hallNumber", session.getHallNumber());
        response.put("seats", booking.getBookedSeats());
        response.put("rows", booking.getRows());
        response.put("seatsPerRow", booking.getSeatsPerRow());
        
        return response;
    }
    
    @PostMapping("/films/{filmId}/sessions/{sessionIndex}/seats/{seatId}/reserve")
    public Map<String, Object> reserveSeat(@PathVariable Long filmId,
                                          @PathVariable int sessionIndex,
                                          @PathVariable int seatId,
                                          @RequestBody Map<String, String> request) {
        Film film = filmService.getFilmById(filmId);
        Session session = film.getSessions().get(sessionIndex);
        Booking booking = session.getBooking();
        
        String userName = request.get("userName");
        boolean success = booking.reserveSeat(seatId, userName);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("seat", booking.getBookedSeats().get(seatId));
        
        return response;
    }
}