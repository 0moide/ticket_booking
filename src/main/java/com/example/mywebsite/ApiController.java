package com.example.mywebsite;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
}