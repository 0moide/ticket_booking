package com.example.mywebsite.entities;

import java.util.ArrayList;

public class Film {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private int duration; // в минутах
    private String posterFileName; // имя файла с постером
    private int minAge; // минимальный возраст
    private ArrayList<Session> sessions;
    
    // Конструкторы
    public Film() {}
    
    public Film(Long id, String title, String description, String genre, 
                int duration, String posterFileName, int minAge) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.duration = duration;
        this.posterFileName = posterFileName;
        this.minAge = minAge;
        this.sessions = new ArrayList<>();
    }
    
    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    
    public String getPosterFileName() { return posterFileName; }
    public void setPosterFileName(String posterFileName) { this.posterFileName = posterFileName; }
    
    public int getMinAge() { return minAge; }
    public void setMinAge(int minAge) { this.minAge = minAge; }

    public ArrayList<Session> getSessions() { return sessions; }
    public void setSessions(ArrayList<Session> sessions) { this.sessions = sessions; } 
    
    // Метод для получения полного URL к изображению
    public String getPosterUrl() {
        return "/images/posters/" + posterFileName;
    }
}