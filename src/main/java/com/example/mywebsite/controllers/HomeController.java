package com.example.mywebsite.controllers;

import com.example.mywebsite.entities.Film;
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
        Film film = filmService.getFilmById(filmId);
        if (film == null || sessionIndex >= film.getSessions().size()) {
            return "redirect:/";
        }
        model.addAttribute("film", film);
        // ИЗМЕНЕНИЕ: используем другое имя вместо "session"
        model.addAttribute("filmSession", film.getSessions().get(sessionIndex));
        model.addAttribute("sessionIndex", sessionIndex);
        return "seating";
    }
}