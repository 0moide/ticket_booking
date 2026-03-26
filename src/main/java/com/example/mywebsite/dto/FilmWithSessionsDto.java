package com.example.mywebsite.dto;

import java.util.List;

public class FilmWithSessionsDto extends FilmDto {
    private List<SessionDto> sessions;

    public FilmWithSessionsDto() {}

    public FilmWithSessionsDto(FilmDto film, List<SessionDto> sessions) {
        super(film.getId(), film.getTitle(), film.getGenre(), film.getDuration(), 
              film.getMinAge(), film.getDescription(), film.getPosterUrl());
        this.sessions = sessions;
    }

    public List<SessionDto> getSessions() { return sessions; }
    public void setSessions(List<SessionDto> sessions) { this.sessions = sessions; }
}