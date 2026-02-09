package com.example.mywebsite;

public class Film {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private int duration; // в минутах
    private String posterUrl;
    private int minAge; // минимальный возраст
    
    // Конструкторы
    public Film() {}
    
    public Film(Long id, String title, String description, String genre, 
                int duration, String posterUrl, int minAge) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.duration = duration;
        this.posterUrl = posterUrl;
        this.minAge = minAge;
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
    
    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    
    public int getMinAge() { return minAge; }
    public void setMinAge(int minAge) { this.minAge = minAge; }
}