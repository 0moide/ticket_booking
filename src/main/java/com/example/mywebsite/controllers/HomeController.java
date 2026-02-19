package com.example.mywebsite.controllers;

import com.example.mywebsite.entities.Film;
import com.example.mywebsite.entities.Session;
import com.example.mywebsite.services.FilmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    
    private final FilmService filmService;
    
    public HomeController(FilmService filmService) {
        this.filmService = filmService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("films", filmService.getAllFilms());
        return "index";
    }
    
    @GetMapping("/film/{id}")
    public String filmDetails(@PathVariable Long id, Model model) {
        Film film = filmService.getFilmById(id);
        if (film == null) {
            return "redirect:/";
        }
        model.addAttribute("film", film);
        return "film-details";
    }
    
    @GetMapping("/session/{filmId}/{sessionIndex}")
    public String seatingPage(@PathVariable Long filmId, 
                            @PathVariable int sessionIndex,
                            Model model) {
        System.out.println("=== DEBUG seatingPage ===");
        System.out.println("filmId: " + filmId);
        System.out.println("sessionIndex: " + sessionIndex);
        
        Film film = filmService.getFilmById(filmId);
        if (film == null) {
            System.out.println("Film not found!");
            return "redirect:/";
        }
        
        System.out.println("Film: " + film.getTitle());
        System.out.println("Sessions size: " + film.getSessions().size());
        
        if (sessionIndex >= film.getSessions().size()) {
            System.out.println("Session index out of bounds");
            return "redirect:/";
        }
        
        Session session = film.getSessions().get(sessionIndex);
        System.out.println("Session found, hall: " + session.getHallNumber());
        System.out.println("Booking exists: " + (session.getBooking() != null));
        
        if (session.getBooking() != null) {
            System.out.println("Booking rows: " + session.getBooking().getRows());
            System.out.println("Booking seats: " + session.getBooking().getSeats().size());
        }
        
        model.addAttribute("film", film);
        model.addAttribute("filmSession", session);
        model.addAttribute("sessionIndex", sessionIndex);
        
        return "seating";
    }
}