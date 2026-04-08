package com.example.mywebsite.services;

import org.springframework.stereotype.Service;

import com.example.mywebsite.entities.Booking;
import com.example.mywebsite.entities.Film;
import com.example.mywebsite.entities.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private List<Film> films = new ArrayList<>();
    private Long currentFilmId = 1L;
    private Long currentSessionId = 1L;
    private Long currentBookingId = 1L;
    
    public FilmService() {
        initializeFilms();
        addSessions();
    }
    
    private void initializeFilms() {
        ArrayList<String> genres = new ArrayList<>();
        genres.add("Научная фантастика");
        genres.add("Приключения");
        films.add(new Film(currentFilmId++, "Интерстеллар", 
                "Фантастический эпос про задыхающуюся Землю, космические полеты и парадоксы времени", 
                genres, 169, 
                "interstellar.jpg", 16));
        
        genres = new ArrayList<>();
        genres.add("Биография");
        genres.add("Драма");
        films.add(new Film(currentFilmId++, "Оппенгеймер", 
                "История создания атомной бомбы.", 
                genres, 180, 
                "oppenheimer.jpg", 18));
        
        genres = new ArrayList<>();
        genres.add("Приключения");
        genres.add("Боевик");
        genres.add("Драма");
        films.add(new Film(currentFilmId++, "Человек-бензопила: история Резе", 
                "Новые приключения Дендзи", 
                genres, 100, 
                "chainsaw_man.jpg", 18));
        
        genres = new ArrayList<>();
        genres.add("Боевик");
        films.add(new Film(currentFilmId++, "Крепкий орешек", 
                "Классический боевик с Брюсом Уиллисом.", 
                genres, 132, 
                "default.jpg", 16));
    }

    private void addSessions() {
        // Интерстеллар
        ArrayList<Session> sessions1 = new ArrayList<>();
        sessions1.add(new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 15, 30), new Booking(currentBookingId++, 5, 10), 1));
        sessions1.add(new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 17, 30), new Booking(currentBookingId++, 5, 10), 1));
        sessions1.add(new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 19, 30), new Booking(currentBookingId++, 5, 10), 1));
        films.get(0).setSessions(sessions1);
        
        // Оппенгеймер
        ArrayList<Session> sessions2 = new ArrayList<>();
        sessions2.add(new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 15, 30), new Booking(currentBookingId++, 5, 10), 2));
        sessions2.add(new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 17, 30), new Booking(currentBookingId++, 5, 10), 2));
        sessions2.add(new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 19, 30), new Booking(currentBookingId++, 5, 10), 2));
        films.get(1).setSessions(sessions2);
        
        // Человек-бензопила
        ArrayList<Session> sessions3 = new ArrayList<>();
        sessions3.add(new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 9, 30), new Booking(currentBookingId++, 5, 10), 1));
        sessions3.add(new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 11, 30), new Booking(currentBookingId++, 5, 10), 1));
        sessions3.add(new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 13, 30), new Booking(currentBookingId++, 5, 10), 1));
        films.get(2).setSessions(sessions3);
        
        // Крепкий орешек
        ArrayList<Session> sessions4 = new ArrayList<>();
        sessions4.add(new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 9, 30), new Booking(currentBookingId++, 5, 10), 2));
        sessions4.add(new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 11, 30), new Booking(currentBookingId++, 5, 10), 2));
        sessions4.add(new Session(currentSessionId++, LocalDateTime.of(2026, 2, 11, 13, 30), new Booking(currentBookingId++, 5, 10), 2));
        films.get(3).setSessions(sessions4);
    }
    
    public List<Film> getAllFilms() {
        return new ArrayList<>(films);
    }
    
    public Film getFilmById(Long id) {
        return films.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}